package com.jmat.dashboard.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmat.dashboard.data.ModuleRepository
import com.jmat.dashboard.data.model.ModuleInstallData
import com.jmat.dashboard.ui.model.ShortcutData
import com.jmat.powertools.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val moduleRepository: ModuleRepository
) : ViewModel() {
    private val installData = moduleRepository.moduleInstallData

    private val shortcuts = userPreferencesRepository.data
        .map { it.shortcutsList }
        .distinctUntilChanged()

    private val shortcutUiData = combineTransform(
        installData,
        shortcuts
    ) { installData, shortcuts ->
        val data = shortcuts.mapNotNull { shortcut ->
            val module = installData.find { it.module.installName == shortcut.moduleName }?.module ?: return@mapNotNull null
            val feature = module.features.find { it.id == shortcut.featureId } ?: return@mapNotNull null

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
        installData,
        shortcutUiData,
    ) { loading, installData, shortcutUiData ->
        emit(
            UiState(
                loading = loading,
                moduleInstallData = installData,
                shortcutFeatures = shortcutUiData
            )
        )
    }

    init {
        viewModelScope.launch(Dispatchers.Default) {
            loading.emit(true)
            delay(5000)
            moduleRepository.downloadModuleInstallData()
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
        val moduleInstallData: List<ModuleInstallData>
    ) {
        companion object {
            val default = UiState(
                loading = false,
                shortcutFeatures = listOf(),
                moduleInstallData = listOf()
            )
        }
    }
}
