package com.jmat.powertools.base.extensions

fun <T> Collection<T>.findIndex(call: (T) -> Boolean): Int {
    val item = find { call(it) }
    return indexOf(item)
}

fun <T> Collection<T>.contains(call: (T) -> Boolean): Boolean {
    val item = find { call(it) }
    return contains(item)
}
