package com.jmat.dashboard.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmat.dashboard.data.ModuleRepository
import com.jmat.dashboard.data.model.Feature
import com.jmat.powertools.UserPreferences
import com.jmat.powertools.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    val uiState = MutableStateFlow(UiState.default)
    val favourites = userPreferencesRepository.data.map { it.favouritesList }
    val installedModules = userPreferencesRepository.data.map { it.modulesList }

    data class UiState(
        val loading: Boolean,
        val favouriteFeatures: List<Feature>
    ) {
        companion object {
            val default = UiState(
                loading = false,
                favouriteFeatures = listOf()
            )
        }
    }
}
