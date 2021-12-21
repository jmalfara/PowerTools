package com.jmat.powertools.base.extensions

import androidx.navigation.NavController
import com.google.android.material.navigationrail.NavigationRailView

fun NavController.setupNavigationRail(
    navigationRailView: NavigationRailView,
    selectedMenuItem: Int
) {
    navigationRailView.selectedItemId = selectedMenuItem
    navigationRailView.setOnItemSelectedListener { item ->
        navigate(item.itemId)
        true
    }
}