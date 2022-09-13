package com.jmat.dashboard.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationBarView
import com.jmat.dashboard.R
import com.jmat.dashboard.di.DaggerDashboardComponent
import com.jmat.dashboard.ui.viewmodel.DashboardViewModel
import com.jmat.powertools.base.di.InjectedViewModelFactory
import com.jmat.powertools.base.extensions.navigateDeeplink
import com.jmat.powertools.modules.dashboard.DashboardModuleDependencies
import com.jmat.powertools.modules.settings.DEEPLINK_SETTINGS
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerDashboardComponent.builder()
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    this,
                    DashboardModuleDependencies::class.java
                )
            ).build().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
    }
}