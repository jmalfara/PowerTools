package com.jmat.dashboard.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.jmat.dashboard.data.ModuleRepository
import com.jmat.powertools.UserPreferences
import com.jmat.powertools.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    val favourites = userPreferencesRepository.data.map { it.favouritesList }
    val installedModules = userPreferencesRepository.data.map { it.modulesList }
}
