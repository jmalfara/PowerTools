package com.jmat.dashboard.ui

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import com.google.android.play.core.splitcompat.SplitCompat
import com.jmat.dashboard.di.DaggerDashboardComponent
import com.jmat.dashboard.ui.screen.DashboardScreen
import com.jmat.dashboard.ui.viewmodel.DashboardViewModel
import com.jmat.powertools.base.compose.theme.AppTheme
import com.jmat.powertools.base.di.InjectedViewModelFactory
import com.jmat.powertools.modules.dashboard.DashboardModuleDependencies
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class DashboardActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        SplitCompat.installActivity(this)
    }

    @Inject
    lateinit var viewModelFactory: InjectedViewModelFactory
    private val viewModel: DashboardViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerDashboardComponent.builder()
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    this,
                    DashboardModuleDependencies::class.java
                )
            ).build().inject(this)
        super.onCreate(savedInstanceState)

        setContent {
            val uiState =
                viewModel.uiState.collectAsState(initial = DashboardViewModel.UiState.default)

            uiState.value.notificationText?.let { notificationText ->
                Toast.makeText(this@DashboardActivity, notificationText, Toast.LENGTH_LONG).show()
                viewModel.consumeNotificationText()
            }

            AppTheme {
                DashboardScreen(
                    shortcuts = uiState.value.shortcutFeatures,
                    moduleInstallData = uiState.value.moduleInstallData,
                    loading = uiState.value.loading,
                    installModule = { module ->
                        viewModel.installModule(module)
                    }
                )
            }
        }
    }
}