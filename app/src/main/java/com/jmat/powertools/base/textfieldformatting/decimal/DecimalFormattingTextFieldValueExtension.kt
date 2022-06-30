package com.jmat.powertools.base.textfieldformatting.decimal

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.jmat.powertools.base.textfieldformatting.TextFieldData
import com.jmat.powertools.base.textfieldformatting.TextFieldSelection
import java.util.*

fun TextFieldValue.formatDecimal(
    locale: Locale
): TextFieldValue {
    val textFieldData = TextFieldData(
        text = text,
        selection = TextFieldSelection(selection.start, selection.end)
    ).formatDecimal(locale)

    with(textFieldData) {
        return copy(
            text = text,
            selection = TextRange(selection.start, selection.end)
        )
    }
}