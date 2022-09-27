package com.jmat.dashboard.ui.model

sealed class ModuleState {
    object Installed: ModuleState()
    object Installing: ModuleState()
    object Uninstalled: ModuleState()
    data class Failed(val error: String): ModuleState()
}