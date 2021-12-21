package com.jmat.powertools.base.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

fun AppCompatActivity.setupSupportActionbar(
    toolbar: Toolbar,
    title: CharSequence? = null,
    navigationMode: NavigationMode
) {
    setSupportActionBar(toolbar)
    supportActionBar?.title = title

    when(navigationMode) {
        NavigationMode.BACK -> {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        NavigationMode.CLOSE -> {
//            supportActionBar?.setHomeAsUpIndicator()
        }
    }
}

enum class NavigationMode {
    BACK,
    CLOSE
}