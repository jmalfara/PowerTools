package com.jmat.dashboard.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jmat.dashboard.R
import com.jmat.dashboard.ui.fragment.DashboardModuleDetailsFragment

class DashboardModuleDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_module_details)

        supportFragmentManager.beginTransaction()
            .add(
                R.id.nav_host_fragment,
                DashboardModuleDetailsFragment().apply {
                    arguments = intent.extras
                }
            )
            .commit()
    }
}