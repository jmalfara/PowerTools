package com.jmat.conversions.ui.model

import java.math.BigDecimal

sealed class ConversionEvent {
    data class UpdateFromAmount(
        val amount: BigDecimal
    ) : ConversionEvent()

    data class UpdateToAmount(
        val amount: BigDecimal
    ) : ConversionEvent()
}