package com.jmat.powertools.base.compose.textfield

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import java.text.DecimalFormatSymbols
import java.util.*

fun TextFieldValue.formatNumber(
    locale: Locale
): TextFieldValue {
    val decimalSeparator = DecimalFormatSymbols.getInstance(locale).decimalSeparator
    val numberMatchRegex = "[-+]?\\d*\\$decimalSeparator\\d+|\\d+\\$decimalSeparator?".toRegex()
    return copy(
        text = numberMatchRegex.find(text, 0)?.groupValues?.first() ?: ""
    )
}

fun TextFieldValue.formatFourDigitCard(
    old: TextFieldValue,
    maxBlocks: Int = 4,
    blockSeparator: String = "-"
): TextFieldValue {
    if (old.text == text) {
        return this
    }

    val blockMatchRegex = "\\d?\\d?\\d?\\d".toRegex()
    val cleaned = text.replace("[^\\d.]".toRegex(), "")
    val blocks = blockMatchRegex.findAll(cleaned, startIndex = 0)
    val isSelectionAtEnd = text.length == selection.max
    val formatted = blocks.take(maxBlocks).foldIndexed("") { index, acc, matchResult ->
        val part = if (index == 0) {
            matchResult.value
        } else {
            "${blockSeparator}${matchResult.value}"
        }
        acc + part
    }

    return copy(
        text = formatted,
        selection = if (isSelectionAtEnd) {
            TextRange(formatted.length)
        } else selection
    )
}