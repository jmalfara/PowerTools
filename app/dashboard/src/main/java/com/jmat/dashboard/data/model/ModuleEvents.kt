package com.jmat.dashboard.data.model

sealed class ModuleEvent {
    object Installing: ModuleEvent()
    object Installed: ModuleEvent()
    object Failed: ModuleEvent()
}