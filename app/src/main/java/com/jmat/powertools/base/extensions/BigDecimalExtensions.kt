package com.jmat.powertools.base.extensions

import java.math.BigDecimal

fun BigDecimal.isZeroScaled() : Boolean {
    return this == BigDecimal.ZERO.setScale(this.scale())
}