package com.jmat.conversions.ui.model

sealed class ConversionEvent {
    data class UpdateFromAmount(
        val amount: String
    ) : ConversionEvent()

    data class UpdateToAmount(
        val amount: String
    ) : ConversionEvent()
}