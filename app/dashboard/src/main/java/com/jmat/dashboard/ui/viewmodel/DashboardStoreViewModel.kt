package com.jmat.dashboard.ui.viewmodel

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmat.dashboard.data.ModuleRepository
import com.jmat.dashboard.ui.model.ModuleData
import com.jmat.powertools.UserPreferences
import com.jmat.powertools.base.extensions.contains
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardStoreViewModel @Inject constructor(
    private val moduleRepository: ModuleRepository,
    private val dataStore: DataStore<UserPreferences>
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState.default)
    val uiState: StateFlow<UiState> = _uiState

    var _showPopular: Boolean = false

    init {
        viewModelScope.launch {
            dataStore.data.map { it.modulesList }.collectLatest {
                fetchStoreDetails()
            }
        }
    }

    fun showPopular(show: Boolean) {
        _showPopular = show
        viewModelScope.launch {
            fetchStoreDetails()
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
                .onSuccess { listings ->
                    run {
                        val modules = if (_showPopular) {
                            listings.popularModules
                        } else listings.newModules

                        _uiState.emit(
                            _uiState.value.copy(
                                modules = modules.map { module ->
                                    ModuleData(
                                        module = module,
                                        installed = dataStore.data.map { it.modulesList }
                                            .first().contains {
                                                module.installName ==  it.installName
                                            }
                                    )
                                }
                            )
                        )
                    }
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
        val modules: List<ModuleData>
    ) {
        companion object {
            val default = UiState(
                loading = false,
                modules = listOf()
            )
        }
    }
}