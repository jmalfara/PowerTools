package com.jmat.dashboard.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.jmat.powertools.UserPreferences
import com.jmat.powertools.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    val favourites = userPreferencesRepository.data.transform { preferences: UserPreferences ->
        emit( preferences.favouritesList)
    }
}
