package com.jmat.powertools.base.extensions

import androidx.navigation.NavController

fun NavController.isRoot(): Boolean {
    return backQueue.size <= 2
}
