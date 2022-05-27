package com.jmat.dashboard.ui.model

import com.jmat.dashboard.data.model.Module
import java.io.Serializable

data class ModuleData(
    val module: Module,
    val installed: Boolean
) : Serializable