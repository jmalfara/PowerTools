package com.jmat.dashboard.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmat.dashboard.data.ModuleRepository
import com.jmat.powertools.data.model.Feature
import com.jmat.powertools.data.model.Module
import com.jmat.powertools.data.preferences.UserPreferencesRepository
import com.jmat.powertools.extensions.toUiFeature
import com.jmat.powertools.extensions.toUiModule
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
            modules.find { it.installName == installedModule.moduleName }
                ?.toUiModule()
        }
        emit(data)
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), 1)

    private val shortcutFeatureData = combineTransform(
        modules,
        shortcuts
    ) { modules, shortcuts ->
        val data = shortcuts.mapNotNull { shortcut ->
            modules.find { it.installName == shortcut.moduleName }
                ?.featuresList?.find { it.id == shortcut.featureId }
                ?.toUiFeature()
        }
        emit(data)
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), 1)

    private val loading = MutableStateFlow(false)

    val uiState = combineTransform(
        loading,
        installedModulesData,
        shortcutFeatureData,
    ) { loading, installedModules, favouriteFeatures ->
        emit(
            UiState(
                loading = loading,
                installedModules = installedModules,
                shortcutFeatures = favouriteFeatures
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

    data class UiState(
        val loading: Boolean,
        val shortcutFeatures: List<Feature>, //TODO Avoid using data objects.
        val installedModules: List<Module>
    )
}
