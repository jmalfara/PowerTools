package com.jmat.conversions.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmat.conversions.ui.model.ConversionEvent
import com.jmat.powertools.base.extensions.isZeroScaled
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

class ConversionL100KmToMPGViewModel : ViewModel() {
    private val scale = 10

    private val _events = MutableSharedFlow<ConversionEvent>(replay = 0)
    val events: Flow<ConversionEvent> = _events

    /*
       L/100Km = GC / mpg

       GC is the conversion ration
       US Gallon GC = 282.48
       Imperial Gallon GC = 235.21
    */
    private val usGallonConstant = BigDecimal(282.48)
    private val impGallonConstant = BigDecimal(235.21)
    private var gallonConstant = impGallonConstant

    fun setUseUsGallon(useUsGallon: Boolean) {
        gallonConstant = if (useUsGallon) {
            usGallonConstant
        } else impGallonConstant

        viewModelScope.launch {
            _events.emit(ConversionEvent.UpdateFromAmount(BigDecimal.ZERO))
            _events.emit(ConversionEvent.UpdateToAmount(BigDecimal.ZERO))
        }
    }

    fun calculateMpg(l100km: BigDecimal) {
        viewModelScope.launch {
            if (l100km.isZeroScaled()) {
                _events.emit(
                    ConversionEvent.UpdateToAmount(BigDecimal.ZERO)
                )
                return@launch
            }

            // mpg = GC / L100Km
            val mpg = gallonConstant.divide(l100km, scale, RoundingMode.FLOOR)
            _events.emit(
                ConversionEvent.UpdateToAmount(mpg)
            )
        }
    }

    fun calculateL100Km(mpg: BigDecimal) {
        viewModelScope.launch {
            if (mpg.isZeroScaled()) {
                _events.emit(
                    ConversionEvent.UpdateFromAmount(BigDecimal.ZERO)
                )
                return@launch
            }

            // l100Km = GC / mgp
            val l100km = gallonConstant.divide(mpg, scale, RoundingMode.FLOOR)
            _events.emit(
                ConversionEvent.UpdateFromAmount(l100km)
            )
        }
    }
}