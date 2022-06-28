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
        "[-+]?\\d*\\$decimalSeparator\\d+|\\d+\\$decimalSeparator?".toRegex()
    private var selfChange = false
    private val acceptedCharacters = listOf(
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '+', decimalSeparator
    )
    private var lastDecimalLocation = -1

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    @Synchronized
    override fun afterTextChanged(s: Editable) {
        if (selfChange) {
            val raw = s.toString()
            val number = numberMatchRegex.find(raw, 0)?.groupValues?.first() ?: ""
            selfChange = false
            if (number != raw) {
                afterTextChanged(s)
            }
            return
        }

        val raw = s.toString().filter { acceptedCharacters.contains(it) }
        val decimalLocations = raw.mapIndexed { index, c ->
            if (c == decimalSeparator) {
                index
            } else null
        }.filterNotNull()

        when(decimalLocations.size) {
            1 -> lastDecimalLocation = decimalLocations.first()
            2 -> {
                // Find new decimal location.
                val indexOfOldDecimal = if (decimalLocations[0] == lastDecimalLocation) {
                    lastDecimalLocation = decimalLocations[1]
                    decimalLocations[0]
                } else {
                    lastDecimalLocation = decimalLocations[0]
                    decimalLocations[1]
                }

                // Remove old decimal
                val newString = raw.removeRange(indexOfOldDecimal, indexOfOldDecimal+1)
                s.replace(0, s.length, newString, 0, newString.length)
                return
            }
        }

        val number = numberMatchRegex.find(raw, 0)?.groupValues?.first() ?: ""
        selfChange = true
        s.replace(0, s.length, number, 0, number.length)
    }
}
