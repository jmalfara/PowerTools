package com.jmat.dashboard.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmat.dashboard.data.ModuleRepository
import com.jmat.powertools.data.model.Feature
import com.jmat.powertools.data.model.Module
import com.jmat.powertools.data.preferences.UserPreferencesRepository
import com.jmat.powertools.extensions.toUiFeature
import com.jmat.powertools.extensions.toUiModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    userPreferencesRepository: UserPreferencesRepository,
    private val moduleRepository: ModuleRepository
) : ViewModel() {
    val modules = userPreferencesRepository.data
        .map { it.modulesList }
        .distinctUntilChanged()

    private val installedModules = userPreferencesRepository.data
        .map { it.installedModulesList }
        .distinctUntilChanged()

    val favourites = userPreferencesRepository.data
        .map { it.favouritesList }
        .distinctUntilChanged()

    private val installedModulesData = combineTransform(
        modules,
        installedModules
    ) { modules, installedModules ->
        val data = installedModules.mapNotNull { installedModule ->
            modules.find { it.installName == installedModule.moduleName }
                ?.toUiModule()
        }
        emit(data)
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), 1)

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
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), 1)

    private val loading = MutableStateFlow(false)

    val uiState = combineTransform(
        loading,
        installedModulesData,
        favouriteFeaturesData,
    ) { loading, installedModules, favouriteFeatures ->
        emit(
            UiState(
                loading = loading,
                installedModules = installedModules,
                favouriteFeatures = favouriteFeatures
            )
        )
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
    }

    fun downloadModuleCatalog() {
        viewModelScope.launch(Dispatchers.Default) {
            loading.emit(true)
            moduleRepository.downloadModules()
            loading.emit(false)
        }
    }

    data class UiState(
        val loading: Boolean,
        val favouriteFeatures: List<Feature>,
        val installedModules: List<Module>
    )
}
