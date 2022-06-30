package com.jmat.powertools.base.textfieldformatting.decimal

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.jmat.powertools.base.textfieldformatting.TextFieldData
import com.jmat.powertools.base.textfieldformatting.TextFieldSelection
import java.util.*

class DecimalFormattingTextWatcher @JvmOverloads constructor(
    val locale: Locale = Locale.getDefault(),
    val editText: EditText
) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable) {
        editText.removeTextChangedListener(this)

        val textFieldData = TextFieldData(
            text = s.toString(),
            selection = TextFieldSelection(
                start = editText.selectionStart,
                end = editText.selectionEnd
            )
        ).formatDecimal(locale)

        with(textFieldData) {
            editText.setText(text)
            val selection = if (selection.start > text.length) {
                text.length
            } else selection.start
            editText.setSelection(selection)
        }

        editText.addTextChangedListener(this)
    }
}