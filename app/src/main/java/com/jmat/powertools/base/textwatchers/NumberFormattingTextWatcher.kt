package com.jmat.powertools.base.textwatchers

import android.text.Editable
import android.text.TextWatcher
import java.text.DecimalFormatSymbols
import java.util.Locale

class NumberFormattingTextWatcher @JvmOverloads constructor(
    locale: Locale = Locale.getDefault()
) : TextWatcher {

    private val decimalSeparator = DecimalFormatSymbols.getInstance(locale).decimalSeparator
    private val numberMatchRegex =
        "[-+]?\\d*${decimalSeparator}\\d+|\\d+$decimalSeparator?".toRegex()
    private var lastValidInput: String? = null
    private var selfChange = false

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    @Synchronized
    override fun afterTextChanged(s: Editable) {
        if (selfChange) {
            selfChange = false
            return
        }

        val raw = s.toString()
        if (raw.isEmpty()) {
            lastValidInput = null
            return
        }

        val matches = numberMatchRegex.matches(raw)
        if (matches) {
            lastValidInput = raw
            return
        } else {
            selfChange = true
            // Match the first group
            val number = numberMatchRegex.find(raw, 0)?.groupValues?.first()
            if (number == null) {
                if (lastValidInput.isNullOrBlank().not()) {
                    s.replace(0, s.length, lastValidInput)
                } else {
                    lastValidInput = null
                    s.replace(0, s.length, "")
                }
            } else {
                lastValidInput = number
                s.replace(0, s.length, number, 0, number.length)
            }
        }
    }
}
