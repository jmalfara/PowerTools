package com.jmat.conversions.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jmat.powertools.base.extensions.contains
import com.jmat.powertools.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch

class ConversionShortcutsViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val featureId: String,
    private val moduleName: String
) : ViewModel() {
    val isShortcut: Flow<Boolean> = userPreferencesRepository.data.transform { data ->
        val favourite = data.shortcutsList.contains { it.featureId == featureId }
        emit(favourite)
    }

    fun toggleShortcut() {
        viewModelScope.launch {
            if (isShortcut.first()) {
                userPreferencesRepository.removeShortcut(featureId)
            } else {
                userPreferencesRepository.addShortcut(
                    moduleName = moduleName,
                    featureId = featureId
                )
            }
        }
    }
}

class ConversionFavouritesViewModelFactory(
    private val userPreferencesRepository: UserPreferencesRepository,
    val featureId: String,
    val moduleName: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConversionShortcutsViewModel::class.java)) {
            return ConversionShortcutsViewModel(
                userPreferencesRepository = userPreferencesRepository,
                featureId = featureId,
                moduleName = moduleName
            ) as T
        } else throw RuntimeException("ConversionKilometersToMilesViewModelFactory not assignable")
    }
}