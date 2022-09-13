package com.jmat.dashboard.ui.model

import com.jmat.dashboard.data.model.Module

data class ModuleInstallData(
    val module: Module,
    val moduleState: ModuleState
)