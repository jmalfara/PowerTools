package com.jmat.powertools.base.textfieldformatting.decimal

import com.jmat.powertools.base.textfieldformatting.TextFieldData
import com.jmat.powertools.base.textfieldformatting.TextFieldSelection
import java.text.DecimalFormatSymbols
import java.util.*

fun TextFieldData.formatDecimal(
    locale: Locale
): TextFieldData {
    var selectionOffset = 0

    val symbols = DecimalFormatSymbols.getInstance(locale)
    val groupingSeparator = symbols.groupingSeparator
    val decimalSeparator = symbols.decimalSeparator
    val numberMatchRegex = "[-+]?\\d*\\$decimalSeparator\\d*|\\d+|[-+]".toRegex()

    val cursorIndex = selection.start - 1
    // Remove group separators
    val cleanedText: String = text
        .let { text ->
            // Remove any new character added that is not a digit or the decimal
            text.getOrNull(cursorIndex)?.let { lastChar ->
                if (lastChar.isDigit().not() &&
                    lastChar != decimalSeparator &&
                    lastChar != groupingSeparator
                ) {
                    selectionOffset--
                    text.replaceRange(cursorIndex, cursorIndex + 1, "")
                } else text
            } ?: text
        }
        .let {
            // Check if the decimal moved.
            if (it.getOrNull(cursorIndex) == decimalSeparator) {
                // Remove any decimal that is not the new decimal
                val filtered = it.filterIndexed { index, char ->
                    val toRemove = (char == decimalSeparator && index != cursorIndex)
                    // Only offset if the cursor is behind the char that changed
                    if (toRemove && index < cursorIndex) {
                        selectionOffset--
                    }
                    toRemove.not()
                }
                filtered
            } else it
        }
        .filter { it.isDigit() || it == decimalSeparator }
        .let { text ->
            // Remove any leading zeros and offset the selection.
            // The exceptions here are for "0.", "0",
            if (text.getOrNull(0) == '0' &&
                text.getOrNull(1) != decimalSeparator &&
                text.length > 1
            ) {
                text.trimStart('0').also { trimmed ->
                    // Offset by the number of zeros removed
                    selectionOffset -= text.length - trimmed.length
                }.ifEmpty {
                    // Avoid empty string. Add back a 0 and move the cursor up.
                    selectionOffset++
                    "0"
                }
            } else text
        }
        .let { text ->
            // "." should become "0."
            if (text.startsWith(decimalSeparator)) {
                selectionOffset++
                "0$text"
            } else text
        }

    val value = numberMatchRegex.find(cleanedText, 0)?.groupValues?.first() ?: ""
    if (value.isEmpty() || value == "$decimalSeparator") {
        return copy(
            text = value
        )
    }

    val formatted = internalFormatDecimal(value, groupingSeparator, decimalSeparator)

    // Get number of formatted grouping symbols before cursor
    val nTextGroupSymbols = text.take(selection.start).count { it == groupingSeparator }
    val nFormattedGroupSymbols = formatted.take(selection.start).count { it == groupingSeparator }
    selectionOffset += nFormattedGroupSymbols - nTextGroupSymbols

    // Calculate the new cursor offsets
    val startSelection = if (selection.start + selectionOffset < 0) {
        0
    } else selection.start + selectionOffset

    val endSelection = if (selection.end + selectionOffset < 0) {
        0
    } else selection.end + selectionOffset

    return copy(
        text = formatted,
        selection = TextFieldSelection(startSelection, endSelection)
    )
}

private fun internalFormatDecimal(
    value: String,
    groupSeparator: Char,
    decimalSeparator: Char
): String {
    val numberSplit = value.split(decimalSeparator)
    val integral = numberSplit.getOrNull(0)
        ?.let { integral ->
            // Break into hundredths
            val blockMatchRegex = "\\d?\\d?\\d".toRegex()
            // Reverse the integral and make blocks. 123,456,789 -> 987,654,321
            blockMatchRegex.findAll(integral.reversed(), startIndex = 0)
                .foldIndexed("") { index, acc, matchResult ->
                    if (index == 0) {
                        matchResult.value
                    } else "$acc$groupSeparator${matchResult.value}"
                }
                .reversed()
        } ?: ""

    val fractional = numberSplit.getOrNull(1) ?: ""
    return if (value.contains(decimalSeparator)) {
        "$integral$decimalSeparator$fractional"
    } else integral
}