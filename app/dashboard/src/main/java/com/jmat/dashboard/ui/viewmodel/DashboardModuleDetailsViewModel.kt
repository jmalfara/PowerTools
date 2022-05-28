package com.jmat.dashboard.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.jmat.dashboard.R
import com.jmat.dashboard.ui.DashboardModuleDetailsActivityArgs
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardModuleDetailsViewModel @AssistedInject constructor(
    @Assisted val savedStateHandle: SavedStateHandle,
    val splitInstallManager: SplitInstallManager
) : ViewModel() {
    private val args = DashboardModuleDetailsActivityArgs.fromSavedStateHandle(savedStateHandle)
    private val _uiState = MutableStateFlow(
        UiState(
            installing = false,
            actionTextRes = if (args.moduleData.installed) {
                R.string.dashboard_details_uninstall
            } else R.string.dashboard_details_install,
            installed = args.moduleData.installed,
            notificationMessage = null
        )
    )
    val uiState: StateFlow<UiState> = _uiState

    fun install() {
        viewModelScope.launch {
        }
    }

    fun uninstall() {
        // TODO Uninstall
    }

    data class UiState(
        val installing: Boolean,
        val actionTextRes: Int,
        val installed: Boolean,
        val notificationMessage: Int?
    )
}