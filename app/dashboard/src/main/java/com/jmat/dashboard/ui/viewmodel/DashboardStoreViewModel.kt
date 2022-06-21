package com.jmat.dashboard.ui.viewmodel

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmat.dashboard.data.ModuleRepository
import com.jmat.dashboard.data.model.Module
import com.jmat.dashboard.ui.model.ListingData
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

    fun showPopular(show: Boolean) {
        viewModelScope.launch {
            fetchStoreDetails(show)
        }
    }

    fun fetchStoreDetails(popular: Boolean) {
        viewModelScope.launch {
            _uiState.emit(
                _uiState.value.copy(
                    loading = true
                )
            )

            val modules = moduleRepository.fetchModules().getOrNull()?.modules

            moduleRepository.fetchModuleListings()
                .onSuccess { listings ->
                    run {
                        _uiState.emit(
                            _uiState.value.copy(
                                listings = listings.popular.map { listing ->
                                    modules?.find {
                                        it.installName == listing.installName
                                    }?.let { module ->
                                        val installed = dataStore.data.map {
                                            it.modulesList
                                        }.first().contains {
                                            it.installName == module.installName
                                        }

                                        ListingData(
                                            module = module,
                                            listing = listing,
                                            installed = installed
                                        )
                                    }
                                }.filterNotNull()
                            )
                        )
                    }
                }
                .onFailure {
                    _uiState.emit(
                        _uiState.value.copy(
                            listings = listOf()
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
        val listings: List<ListingData>
    ) {
        companion object {
            val default = UiState(
                loading = false,
                listings = listOf()
            )
        }
    }
}