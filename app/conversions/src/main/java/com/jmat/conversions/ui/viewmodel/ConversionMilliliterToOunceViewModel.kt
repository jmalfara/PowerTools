package com.jmat.conversions.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmat.conversions.ui.model.ConversionEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

class ConversionMilliliterToOunceViewModel : ViewModel() {
    private val scale = 10
    private val ouncePerMil = BigDecimal(0.033814)
    private val _events = MutableSharedFlow<ConversionEvent>(replay = 0)
    val events: Flow<ConversionEvent> = _events

    fun setMilliliters(mils: BigDecimal) {
        viewModelScope.launch {
            val totalOunces = mils.multiply(ouncePerMil).setScale(scale, RoundingMode.FLOOR)
            _events.emit(
                ConversionEvent.UpdateToAmount(totalOunces)
            )
        }
    }

    fun setOunces(ounces: BigDecimal) {
        viewModelScope.launch {
            val totalMils = ounces.multiply(
                BigDecimal.ONE.divide(ouncePerMil, scale, RoundingMode.FLOOR)
            ).setScale(scale, RoundingMode.FLOOR)
            _events.emit(
                ConversionEvent.UpdateFromAmount(totalMils)
            )
        }
    }
}