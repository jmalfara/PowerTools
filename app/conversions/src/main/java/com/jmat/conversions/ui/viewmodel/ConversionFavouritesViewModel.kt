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
    private val favouriteId: String
) : ViewModel() {
    val isFavourite: Flow<Boolean> = userPreferencesRepository.data.transform { data ->
        val favourite = data.favouritesList.contains { it.id == favouriteId }
        emit(favourite)
    }

    fun toggleFavourite() {
        viewModelScope.launch {
            if (isFavourite.first()) {
                userPreferencesRepository.removeFavourite(favouriteId)
            } else {
                userPreferencesRepository.addFavorite(
                    id = favouriteId,
                    deeplink = "powertools://conversion/km_to_m"
                )
            }
        }
    }
}

class ConversionFavouritesViewModelFactory(
    private val userPreferencesRepository: UserPreferencesRepository,
    val favouriteId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConversionFavouritesViewModel::class.java)) {
            return ConversionFavouritesViewModel(
                userPreferencesRepository = userPreferencesRepository,
                favouriteId = favouriteId
            ) as T
        } else throw RuntimeException("ConversionKilometersToMilesViewModelFactory not assignable")
    }
}