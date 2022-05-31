package com.jmat.dashboard.ui.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmat.dashboard.R
import com.jmat.dashboard.data.ModuleRepository
import com.jmat.dashboard.ui.DashboardModuleDetailsActivityArgs
import com.jmat.powertools.base.data.TextResource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class DashboardModuleDetailsViewModel @AssistedInject constructor(
    @Assisted val savedStateHandle: SavedStateHandle,
    private val moduleRepository: ModuleRepository
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

    fun installModule(module: String) {
        viewModelScope.launch {
            _uiState.emit(
                _uiState.value.copy(
                    installing = true
                )
            )

            moduleRepository.installModule(module)
                .onSuccess {
                    _uiState.emit(
                        _uiState.value.copy(
                            installed = true,
                            notificationMessage = TextResource(
                                R.string.dashboard_details_installed
                            )
                        )
                    )
                }
                .onFailure {
                    Log.d("Modules", it.toString())
                    _uiState.emit(
                        _uiState.value.copy(
                            notificationMessage = TextResource(it.toString())
                        )
                    )
                }

            _uiState.emit(
                _uiState.value.copy(
                    installing = false
                )
            )
        }
    }

    fun uninstallModule(module: String) {
        viewModelScope.launch {
            moduleRepository.uninstallModule(module)
                .onSuccess {
                    _uiState.emit(
                        _uiState.value.copy(
                            installed = false,
                            notificationMessage = TextResource(
                                R.string.dashboard_details_uninstalling
                            )
                        )
                    )
                }
                .onFailure {
                    Log.d("Modules", it.toString())
                    _uiState.emit(
                        _uiState.value.copy(
                            notificationMessage = TextResource(it.toString())
                        )
                    )
                }
        }
    }
    
    fun consumeNotificationMessage() {
        viewModelScope.launch {
            _uiState.emit(
                _uiState.value.copy(
                    notificationMessage = null
                )
            )
        }
    }

    data class UiState(
        val installing: Boolean,
        val actionTextRes: Int,
        val installed: Boolean,
        val notificationMessage: TextResource?
    )
}