package com.jmat.dashboard.ui.model

sealed class ModuleState {
    data object Installed: ModuleState()
    data object Installing: ModuleState()
    data object Uninstalled: ModuleState()
    data class Failed(val error: String): ModuleState()
}