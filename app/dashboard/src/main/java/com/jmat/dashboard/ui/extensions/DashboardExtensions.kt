package com.jmat.dashboard.ui.extensions

import android.app.Activity
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.jmat.dashboard.R
import com.jmat.dashboard.ui.model.TabData

fun Activity.setupAppbar(
    tabs: List<TabData>,
    onTabSelected: (tab: TabLayout.Tab) -> Unit = {}
) {
    val tabLayout = findViewById<TabLayout>(R.id.tabs)
    tabLayout.removeAllTabs()
    tabs.forEach {
        tabLayout.addTab(tabLayout.newTab().apply {
            id = it.id
            text = it.text
        })
    }

    tabLayout.clearOnTabSelectedListeners()
    tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.let {
                onTabSelected(it)
            }
        }
        override fun onTabUnselected(tab: TabLayout.Tab?) {}
        override fun onTabReselected(tab: TabLayout.Tab?) {}
    })
    tabLayout.isVisible = tabs.isEmpty().not()
}