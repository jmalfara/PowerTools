package com.jmat.dashboard.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmat.dashboard.data.ModuleRepository
import com.jmat.dashboard.data.model.Feature
import com.jmat.dashboard.data.model.Listing
import com.jmat.dashboard.data.model.Module
import com.jmat.dashboard.ui.model.ListingData
import com.jmat.powertools.base.extensions.contains
import com.jmat.powertools.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardStoreViewModel @Inject constructor(
    private val moduleRepository: ModuleRepository,
    userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState.default)
    val uiState: StateFlow<UiState> = _uiState

    private val installedModules = userPreferencesRepository.data.map { it.installedModulesList }
    private val modules = userPreferencesRepository.data.map { it.modulesList }
    private val storeListings = MutableStateFlow(listOf<Listing>())

    private val listingData = combineTransform(
        installedModules,
        modules,
        storeListings
    ) { installedModules, modules, storeListings ->
        val listingData = storeListings.mapNotNull { listing ->
            val installed = installedModules.contains {
                it.moduleName == listing.installName
            }

            val module = modules?.find { it.installName == listing.installName }
                ?: return@mapNotNull null

            ListingData(
                module = Module(
                    name = module.name,
                    author = module.author,
                    iconUrl = module.iconUrl,
                    shortDescription = module.shortDescription,
                    installName = module.installName,
                    entrypoint = module.entrypoint,
                    features = module.featuresList.map {
                        Feature(
                            id = it.id,
                            title = it.title,
                            description = it.description,
                            module = it.module,
                            iconUrl = it.iconUrl,
                            entrypoint = it.entrypoint
                        )
                    },
                ),
                listing = listing,
                installed = installed
            )
        }
        emit(listingData)
    }



    init {
        viewModelScope.launch {
            listingData.collectLatest {
                _uiState.emit(
                    _uiState.value.copy(
                        listings = it
                    )
                )
            }
        }
    }

    fun fetchStoreDetails(popular: Boolean) {
        viewModelScope.launch {
            _uiState.emit(
                _uiState.value.copy(
                    loading = true
                )
            )

            moduleRepository.fetchModuleListings()
                .onSuccess { listings ->
                    run {
                        val items = if (popular) {
                            listings.popular
                        } else listings.new
                        storeListings.emit(items)
                    }
                }
                .onFailure { /* No Op */ }

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