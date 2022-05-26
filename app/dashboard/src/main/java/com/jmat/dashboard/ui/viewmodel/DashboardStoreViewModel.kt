package com.jmat.dashboard.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmat.dashboard.data.ModuleRepository
import com.jmat.dashboard.data.model.Module
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardStoreViewModel @Inject constructor(
    private val moduleRepository: ModuleRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState.default)
    val uiState: StateFlow<UiState> = _uiState

    fun fetchStoreDetails() {
        viewModelScope.launch {
            _uiState.emit(
                _uiState.value.copy(
                    loading = true
                )
            )

            moduleRepository.fetchModules()
                .onSuccess {
                    _uiState.emit(
                        _uiState.value.copy(
                            modules = it.modules
                        )
                    )
                }
                .onFailure {
                    _uiState.emit(
                        _uiState.value.copy(
                            modules = listOf()
                        )
                    )
                }

            _uiState.emit(
                _uiState.value.copy(
                    loading = false
                )
            )
        }
    }

    data class UiState(
        val loading: Boolean,
        val modules: List<Module>
    ) {
        companion object {
            val default = UiState(
                loading = true,
                modules = listOf()
            )
        }
    }
}