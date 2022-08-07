package com.jmat.dashboard.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmat.dashboard.data.ModuleRepository
import com.jmat.dashboard.data.model.Feature
import com.jmat.dashboard.data.model.Module
import com.jmat.dashboard.ui.model.ShortcutData
import com.jmat.powertools.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    val userPreferencesRepository: UserPreferencesRepository,
    private val moduleRepository: ModuleRepository
) : ViewModel() {
    val modules = userPreferencesRepository.data
        .map { it.modulesList }
        .distinctUntilChanged()

    private val installedModules = userPreferencesRepository.data
        .map { it.installedModulesList }
        .distinctUntilChanged()

    val shortcuts = userPreferencesRepository.data
        .map { it.shortcutsList }
        .distinctUntilChanged()

    private val installedModulesData = combineTransform(
        modules,
        installedModules
    ) { modules, installedModules ->
        val data = installedModules.mapNotNull { installedModule ->
            val module = modules.find { it.installName == installedModule.moduleName } ?: return@mapNotNull null
            Module(
                name = module.name,
                author = module.author,
                iconUrl = module.iconUrl,
                shortDescription = module.shortDescription,
                installName = module.installName,
                entrypoint = module.entrypoint,
                features = module.featuresList.map {
                    Feature(
                        id = it.id,
                        title = it.title,
                        description = it.description,
                        module = it.module,
                        iconUrl = it.iconUrl,
                        entrypoint = it.entrypoint
                    )
                }
            )
        }
        emit(data)
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), 1)

    private val shortcutUiData = combineTransform(
        modules,
        shortcuts
    ) { modules, shortcuts ->
        val data = shortcuts.mapNotNull { shortcut ->
            val module = modules.find { it.installName == shortcut.moduleName } ?: return@mapNotNull null
            val feature = module.featuresList.find { it.id == shortcut.featureId } ?: return@mapNotNull null

            ShortcutData(
                id = shortcut.id,
                name = feature.title,
                action = feature.entrypoint,
                icon = feature.iconUrl
            )
        }
        emit(data)
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), 1)

    private val loading = MutableStateFlow(false)

    val uiState = combineTransform(
        loading,
        installedModulesData,
        shortcutUiData,
    ) { loading, installedModules, shortcutUiData ->
        emit(
            UiState(
                loading = loading,
                installedModules = installedModules,
                shortcutFeatures = shortcutUiData
            )
        )
    }

    init {
        viewModelScope.launch {
            modules.collectLatest {
                // This is here if the user has cleared the app data.
                // We need to re-download the module catalog
                if (it.isEmpty()) {
                    downloadModuleCatalog()
                }
            }
        }
    }

    fun downloadModuleCatalog() {
        viewModelScope.launch(Dispatchers.Default) {
            loading.emit(true)
            moduleRepository.downloadModules()
            loading.emit(false)
        }
    }

    fun reorderShortcuts(shortcuts: List<ShortcutData>) {
        viewModelScope.launch {
            userPreferencesRepository.updateShortcuts(
                ids = shortcuts.map { it.id }
            )
        }
    }

    data class UiState(
        val loading: Boolean,
        val shortcutFeatures: List<ShortcutData>,
        val installedModules: List<Module>
    )
}
