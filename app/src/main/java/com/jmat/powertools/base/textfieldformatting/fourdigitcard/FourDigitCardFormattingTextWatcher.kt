package com.jmat.powertools.base.textfieldformatting.fourdigitcard

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.jmat.powertools.base.textfieldformatting.TextFieldData
import com.jmat.powertools.base.textfieldformatting.TextFieldSelection

class FourDigitCardFormattingTextWatcher @JvmOverloads constructor(
    private val editText: EditText,
    private val maxBlocks: Int = 4,
    private val blockSeparator: String = "-"
) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    @Synchronized
    override fun afterTextChanged(s: Editable) {
        editText.removeTextChangedListener(this)

        val formatted = FourDigitCardFormatting(
            maxBlocks = maxBlocks,
            blockSeparator = blockSeparator
        ).format(
            textFieldData = TextFieldData(
                text = s.toString(),
                selection = TextFieldSelection(
                    start = editText.selectionStart,
                    end = editText.selectionEnd
                )
            )
        )

        with(formatted) {
            editText.setText(text)
            val selection = if (selection.start > text.length) {
                text.length
            } else selection.start
            editText.setSelection(selection)
        }

        editText.addTextChangedListener(this)
    }
}
