package com.jmat.powertools.base.textfieldformatting

import android.text.Selection

data class TextFieldData(
    val text: String,
    val selection: TextFieldSelection
)

data class TextFieldSelection(
    val start: Int,
    val end: Int = start
)

