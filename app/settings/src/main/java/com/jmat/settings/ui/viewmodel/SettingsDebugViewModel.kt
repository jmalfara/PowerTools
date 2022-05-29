package com.jmat.settings.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.google.android.play.core.splitinstall.SplitInstallManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SettingsDebugViewModel @Inject constructor(
    splitInstallManager: SplitInstallManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState(
        installedModules = splitInstallManager.installedModules.fold("") { acc, s ->
            "$acc\n$s"
        }
    ))
    val uiState: StateFlow<UiState> = _uiState

    data class UiState(
        val installedModules: String
    )
}