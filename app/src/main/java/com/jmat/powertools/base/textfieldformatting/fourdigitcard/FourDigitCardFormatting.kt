package com.jmat.powertools.base.textfieldformatting.fourdigitcard

import com.jmat.powertools.base.textfieldformatting.TextFieldData
import com.jmat.powertools.base.textfieldformatting.TextFieldSelection
import java.util.Locale

class FourDigitCardFormatting(
    private val maxBlocks: Int = 4,
    private val blockSeparator: String = "-"
) {
    fun format(textFieldData: TextFieldData): TextFieldData {
        val text = textFieldData.text
        val selection = textFieldData.selection

        val blockMatchRegex = "\\d?\\d?\\d?\\d".toRegex()
        val cleaned = text.replace("[^\\d.]".toRegex(), "")
        val blocks = blockMatchRegex.findAll(cleaned, startIndex = 0)
        val isSelectionAtEnd = text.length == selection.start
        val formatted = blocks.take(maxBlocks).foldIndexed("") { index, acc, matchResult ->
            val part = if (index == 0) {
                matchResult.value
            } else {
                "${blockSeparator}${matchResult.value}"
            }
            acc + part
        }

        return TextFieldData(
            text = formatted,
            selection = if (isSelectionAtEnd) {
                TextFieldSelection(formatted.length)
            } else selection
        )
    }
}