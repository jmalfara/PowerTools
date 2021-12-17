package com.jmat.dashboard.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jmat.powertools.UserPreferences
import com.jmat.powertools.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.transform

class DashboardViewModel(
    userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    val favourites = userPreferencesRepository.data.transform { preferences: UserPreferences ->
        emit( preferences.favouritesList)
    }
}

class DashboardViewModelFactory(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(
                userPreferencesRepository = userPreferencesRepository
            ) as T
        } else throw RuntimeException("DashboardViewModel not assignable")
    }
}