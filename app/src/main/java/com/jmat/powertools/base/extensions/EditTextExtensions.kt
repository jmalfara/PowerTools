package com.jmat.powertools.base.extensions

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun EditText.addFocusedOnTextChangeListener(afterTextChange: (Editable?) -> Unit) {
    val existingListener: View.OnFocusChangeListener? = onFocusChangeListener
    val listener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            afterTextChange(s)
        }
    }

    setOnFocusChangeListener { v, hasFocus ->
        existingListener?.onFocusChange(v, hasFocus)
        if (hasFocus) {
           addTextChangedListener(listener)
        } else removeTextChangedListener(listener)
    }
}

fun TextInputLayout.showEndIconOnFocus() {
    val existingListener: View.OnFocusChangeListener? = editText?.onFocusChangeListener
    editText?.setOnFocusChangeListener { v, hasFocus ->
        existingListener?.onFocusChange(v, hasFocus)
        if (editText?.text?.toString().isNullOrEmpty().not()) {
            isEndIconVisible = hasFocus
        }
    }
}