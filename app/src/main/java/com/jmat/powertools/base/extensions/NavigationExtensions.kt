package com.jmat.powertools.base.extensions

import android.annotation.SuppressLint
import androidx.navigation.NavController

@SuppressLint("RestrictedApi")
fun NavController.isRoot(): Boolean {
    return currentBackStack.value.size <= 2
}
