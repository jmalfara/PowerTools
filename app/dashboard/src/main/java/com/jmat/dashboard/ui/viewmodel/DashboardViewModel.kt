package com.jmat.dashboard.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmat.dashboard.data.ModuleRepository
import com.jmat.dashboard.data.model.Module
import com.jmat.dashboard.ui.model.ModuleInstallData
import com.jmat.dashboard.ui.model.ShortcutData
import com.jmat.powertools.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    userPreferencesRepository: UserPreferencesRepository,
    private val moduleRepository: ModuleRepository
) : ViewModel() {
    private val installData = moduleRepository.moduleInstallData
    private val shortcuts = userPreferencesRepository.shortcuts
    private val loading = MutableStateFlow(false)
    private val notificationText = MutableStateFlow<String?>(null)

    val uiState = combineTransform(
        loading,
        installData,
        shortcuts,
        notificationText
    ) { loading, installData, shortcuts, notificationText ->
        val shortcutsFeatures = shortcuts.mapNotNull { shortcut ->
            val module = installData.find { it.module.installName == shortcut.moduleName }?.module ?: return@mapNotNull null
            val feature = module.features.find { it.id == shortcut.featureId } ?: return@mapNotNull null

            ShortcutData(
                id = shortcut.id,
                name = feature.title,
                description = feature.description,
                action = feature.entrypoint,
                icon = feature.iconUrl
            )
        }

        emit(
            UiState(
                loading = loading,
                moduleInstallData = installData,
                shortcutFeatures = shortcutsFeatures,
                notificationText = notificationText
            )
        )
    }

    init {
        viewModelScope.launch(Dispatchers.Default) {
            loading.update { true }
            moduleRepository.downloadModuleInstallData()
            loading.update { false }
        }
    }

    fun installModule(module: Module) {
        viewModelScope.launch {
            moduleRepository.installModule(module)
                .onSuccess { }
                .onFailure {
                    notificationText.update { it.toString() }
                }
        }
    }

    fun uninstallModule(module: Module) {
        viewModelScope.launch {
            moduleRepository.uninstallModule(module.installName)
                .onSuccess {
                    notificationText.update {"Module has been marked for removal" }
                }
                .onFailure {
                    notificationText.update {"We could not remove the module at this time" }
                }
        }
    }

//    fun reorderShortcuts(shortcuts: List<ShortcutData>) {
//        viewModelScope.launch {
//            userPreferencesRepository.updateShortcuts(
//                ids = shortcuts.map { it.id }
//            )
//        }
//    }

    fun consumeNotificationText() {
        viewModelScope.launch {
            notificationText.update { null }
        }
    }

    data class UiState(
        val loading: Boolean,
        val shortcutFeatures: List<ShortcutData>,
        val moduleInstallData: List<ModuleInstallData>,
        val notificationText: String?
    ) {
        companion object {
            val default = UiState(
                loading = false,
                shortcutFeatures = listOf(),
                moduleInstallData = listOf(),
                notificationText = null
            )
        }
    }
}
