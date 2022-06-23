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

class ConversionFavouritesViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val featureId: String,
    private val moduleName: String
) : ViewModel() {
    val isFavourite: Flow<Boolean> = userPreferencesRepository.data.transform { data ->
        val favourite = data.favouritesList.contains { it.featureId == featureId }
        emit(favourite)
    }

    fun toggleFavourite() {
        viewModelScope.launch {
            if (isFavourite.first()) {
                userPreferencesRepository.removeFavourite(featureId)
            } else {
                userPreferencesRepository.addFavorite(
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
        if (modelClass.isAssignableFrom(ConversionFavouritesViewModel::class.java)) {
            return ConversionFavouritesViewModel(
                userPreferencesRepository = userPreferencesRepository,
                featureId = featureId,
                moduleName = moduleName
            ) as T
        } else throw RuntimeException("ConversionKilometersToMilesViewModelFactory not assignable")
    }
}