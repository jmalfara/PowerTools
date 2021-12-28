package com.jmat.settings.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmat.powertools.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsLandingViewModel @Inject constructor (
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    fun clearData() {
        viewModelScope.launch {
            userPreferencesRepository.clear()
        }
    }
}