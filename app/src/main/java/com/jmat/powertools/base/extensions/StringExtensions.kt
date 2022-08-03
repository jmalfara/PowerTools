package com.jmat.powertools.base.extensions

import java.math.BigDecimal
import java.math.MathContext

fun String.toCleanBigDecimal(
    context: MathContext = MathContext.DECIMAL64
): BigDecimal {
    val re = Regex("[^\\d.]")
    return re.replace(this, "").toBigDecimalOrNull(context) ?: BigDecimal.ZERO
}
