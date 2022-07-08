package com.jmat.powertools.base.textfieldformatting

data class TextFieldData(
    val text: String,
    val selection: TextFieldSelection
)

data class TextFieldSelection(
    val start: Int,
    val end: Int = start
)

