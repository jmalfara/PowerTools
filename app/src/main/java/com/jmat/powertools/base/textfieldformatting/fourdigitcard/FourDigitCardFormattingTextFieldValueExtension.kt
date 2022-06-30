package com.jmat.powertools.base.textfieldformatting.fourdigitcard

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.jmat.powertools.base.textfieldformatting.TextFieldData
import com.jmat.powertools.base.textfieldformatting.TextFieldSelection

fun TextFieldValue.formatFourDigitCard(
    maxBlocks: Int = 4,
    blockSeparator: String = "-"
): TextFieldValue {

    val formatted = FourDigitCardFormatting(
        maxBlocks = maxBlocks,
        blockSeparator = blockSeparator
    ).format(
        textFieldData = TextFieldData(
            text = text,
            selection = TextFieldSelection(
                start = selection.start,
                end = selection.end
            )
        )
    )

    with(formatted) {
        return copy(
            text = text,
            selection = TextRange(selection.start, selection.end)
        )
    }
}