package com.jmat.dashboard.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationBarView
import com.jmat.dashboard.R
import com.jmat.powertools.base.extensions.navigateDeeplink
import com.jmat.powertools.modules.settings.DEEPLINK_SETTINGS

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val navigationRail = findViewById<NavigationBarView>(R.id.navigation_rail)
        navigationRail.setupWithNavController(navController)

        navigationRail.menu.findItem(R.id.settings).setOnMenuItemClickListener {
            navigateDeeplink(DEEPLINK_SETTINGS)
            true
        }
    }
}