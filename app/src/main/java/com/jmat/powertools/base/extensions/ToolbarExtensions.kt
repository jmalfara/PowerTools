package com.jmat.powertools.base.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.jmat.powertools.R

fun AppCompatActivity.setupSupportActionbar(
    toolbar: Toolbar,
    title: CharSequence? = null,
    navigationMode: NavigationMode
) {
    setSupportActionBar(toolbar)
    supportActionBar?.title = title

    when(navigationMode) {
        NavigationMode.BACK -> {
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }
        }
        NavigationMode.CLOSE -> {
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_24)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationOnClickListener {
                finish()
            }
        }
    }
}

fun Fragment.setupToolbar(
    toolbar: Toolbar,
    title: CharSequence? = null,
    navigationMode: NavigationMode,
) : Toolbar {
    title?.let { toolbar.title = it }

    when(navigationMode) {
        NavigationMode.BACK -> {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24)
            toolbar.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }
        NavigationMode.CLOSE -> {
            toolbar.setNavigationIcon(R.drawable.ic_close_24)
            toolbar.setNavigationOnClickListener {
                requireActivity().finish()
            }
        }
    }

    return toolbar
}

enum class NavigationMode {
    BACK,
    CLOSE
}