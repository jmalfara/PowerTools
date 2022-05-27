package com.jmat.dashboard.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.jmat.dashboard.data.ModuleRepository
import com.jmat.dashboard.data.model.Module
import com.jmat.dashboard.ui.model.ModuleData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardStoreViewModel @Inject constructor(
    private val moduleRepository: ModuleRepository,
    private val splitInstallManager: SplitInstallManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState.default)
    val uiState: StateFlow<UiState> = _uiState

    init {
        splitInstallManager.installedModules.forEach {
            Log.d("SplitManager", it)
        }
    }
    fun showPopular(show: Boolean) {
        viewModelScope.launch {
            _uiState.emit(
                _uiState.value.copy(
                    showingPopular = show
                )
            )
        }
    }

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
                            newModules = mapModules(it.newModules),
                            popularModules = mapModules(it.popularModules)
                        )
                    )
                }
                .onFailure {
                    _uiState.emit(
                        _uiState.value.copy(
                            newModules = listOf(),
                            popularModules = listOf()
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

    private fun mapModules(modules: List<Module>): List<ModuleData> {
        return modules.map {
            ModuleData(
                module = it,
                installed = splitInstallManager.installedModules.contains(it.name.lowercase())
            )
        }
    }

    data class UiState(
        val loading: Boolean,
        val showingPopular: Boolean,
        val popularModules: List<ModuleData>,
        val newModules: List<ModuleData>
    ) {
        companion object {
            val default = UiState(
                loading = true,
                showingPopular = true,
                popularModules = listOf(),
                newModules = listOf()
            )
        }
    }
}