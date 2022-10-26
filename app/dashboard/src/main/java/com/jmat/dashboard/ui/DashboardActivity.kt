package com.jmat.dashboard.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitcompat.SplitCompat
import com.jmat.dashboard.di.DaggerDashboardComponent
import com.jmat.dashboard.ui.screen.DashboardScreen
import com.jmat.dashboard.ui.stateholders.rememberDashboardState
import com.jmat.powertools.base.compose.theme.AppTheme
import com.jmat.powertools.base.di.daggerViewModel
import com.jmat.powertools.modules.dashboard.DashboardModuleDependencies
import dagger.hilt.android.EntryPointAccessors

class DashboardActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        SplitCompat.installActivity(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val component = DaggerDashboardComponent
            .builder()
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    this,
                    DashboardModuleDependencies::class.java
                )
            )
            .build()
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                DashboardScreen(
                    stateHolder = rememberDashboardState(
                        viewModel = daggerViewModel { component.getViewModel() }
                    )
                )
            }
        }
    }
}