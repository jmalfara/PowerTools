package com.jmat.conversions.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jmat.conversions.ui.model.ConversionEvent
import com.jmat.powertools.base.extensions.contains
import com.jmat.powertools.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

class ConversionKilometersToMilesViewModel : ViewModel() {
    private val scale = 10
    private val milesPerKm = BigDecimal(0.621371)
    private val _events = MutableSharedFlow<ConversionEvent>(replay = 0)
    val events: Flow<ConversionEvent> = _events

    fun setKilometers(kilometers: BigDecimal) {
        viewModelScope.launch {
            val totalMiles = kilometers.multiply(milesPerKm).setScale(scale, RoundingMode.FLOOR)
            _events.emit(
                ConversionEvent.UpdateToAmount(totalMiles)
            )
        }
    }

    fun setMiles(miles: BigDecimal) {
        viewModelScope.launch {
            val totalKms = miles.multiply(
                BigDecimal.ONE.divide(milesPerKm, scale, RoundingMode.FLOOR)
            ).setScale(scale, RoundingMode.FLOOR)
            _events.emit(
                ConversionEvent.UpdateFromAmount(totalKms)
            )
        }
    }
}
