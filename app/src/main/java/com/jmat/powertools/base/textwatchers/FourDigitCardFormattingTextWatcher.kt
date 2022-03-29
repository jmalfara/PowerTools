package com.jmat.powertools.base.textwatchers

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import java.text.DecimalFormatSymbols
import java.util.Locale

class FourDigitCardFormattingTextWatcher @JvmOverloads constructor(
    private val maxBlocks: Int = 4,
    private val blockSeparator: String = "-"
) : TextWatcher {
    private val blockMatchRegex = "[0-9]?[0-9]?[0-9]?[0-9]".toRegex()
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

        val cleaned = raw.replace("[^\\d.]".toRegex(), "")
        val blocks = blockMatchRegex.findAll(cleaned, startIndex = 0)
        val cardNumber = blocks.take(maxBlocks).foldIndexed("") { index, acc, matchResult ->
            val part = if (index == 0) {
                matchResult.value
            } else {
                "${blockSeparator}${matchResult.value}"
            }
            acc + part
        }

        selfChange = true
        lastValidInput = cardNumber
        s.replace(0, s.length, cardNumber, 0, cardNumber.length)
    }
}
