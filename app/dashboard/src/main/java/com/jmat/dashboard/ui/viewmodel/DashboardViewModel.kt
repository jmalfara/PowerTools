package com.jmat.dashboard.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmat.dashboard.data.ModuleRepository
import com.jmat.powertools.data.model.Feature
import com.jmat.powertools.data.model.Module
import com.jmat.powertools.data.preferences.UserPreferencesRepository
import com.jmat.powertools.extensions.toUiFeature
import com.jmat.powertools.extensions.toUiModule
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    userPreferencesRepository: UserPreferencesRepository,
    private val moduleRepository: ModuleRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState.default)
    val uiState: StateFlow<UiState> = _uiState

    val modules = userPreferencesRepository.data.map { it.modulesList }
    private val installedModules = userPreferencesRepository.data.map { it.installedModulesList }
    val favourites = userPreferencesRepository.data.map { it.favouritesList }

    private val installedModulesData = combineTransform(
        modules,
        installedModules
    ) { modules, installedModules ->
        val data = installedModules.mapNotNull { installedModule ->
            modules.find { it.installName == installedModule.moduleName }
                ?.toUiModule()
        }
        emit(data)
    }

    private val favouriteFeaturesData = combineTransform(
        modules,
        favourites
    ) { modules, favourites ->
        val data = favourites.mapNotNull { favourite ->
            modules.find { it.installName == favourite.moduleName }
                ?.featuresList?.find { it.id == favourite.featureId }
                ?.toUiFeature()
        }
        emit(data)
    }

    init {
        viewModelScope.launch {
            modules.collectLatest {
                // This is here if the user has cleared the app data.
                // We need to re-download the module catalog
                if (it.isEmpty()) {
                    downloadModuleCatalog()
                }
            }
        }

        viewModelScope.launch {
            installedModulesData.collectLatest {
                _uiState.emit(
                    _uiState.value.copy(
                        installedModules = it
                    )
                )
            }
        }

        viewModelScope.launch {
            favouriteFeaturesData.collectLatest {
                _uiState.emit(
                    _uiState.value.copy(
                        favouriteFeatures = it
                    )
                )
            }
        }
    }

    fun downloadModuleCatalog() {
        viewModelScope.launch {
            _uiState.emit(
                _uiState.value.copy(
                    loading = true
                )
            )

            moduleRepository.downloadModules()

            _uiState.emit(
                _uiState.value.copy(
                    loading = false
                )
            )
        }
    }

    data class UiState(
        val loading: Boolean,
        val favouriteFeatures: List<Feature>,
        val installedModules: List<Module>
    ) {
        companion object {
            val default = UiState(
                loading = false,
                favouriteFeatures = listOf(),
                installedModules = listOf()
            )
        }
    }
}
