package com.jmat.system.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.play.core.splitcompat.SplitCompat
import com.jmat.powertools.base.compose.theme.AppTheme
import com.jmat.powertools.data.preferences.UserPreferencesRepository
import com.jmat.powertools.modules.system.DEEPLINK_SYSTEM
import com.jmat.powertools.modules.system.DEEPLINK_SYSTEM_DEEPLINK
import com.jmat.powertools.modules.system.DEEPLINK_SYSTEM_INTENT
import com.jmat.powertools.modules.system.SystemModuleDependencies
import com.jmat.system.ID_SYSTEM_ACTION_NAVIGATOR
import com.jmat.system.ID_SYSTEM_DEEPLINK_NAVIGATOR
import com.jmat.system.SYSTEM_MODULE_NAME
import com.jmat.system.di.DaggerSystemComponent
import com.jmat.system.ui.screen.SystemDeeplinkScreen
import com.jmat.system.ui.screen.SystemIntentScreen
import com.jmat.system.ui.screen.SystemLandingScreen
import com.jmat.system.ui.viewmodel.SystemShortcutsViewModel
import com.jmat.system.ui.viewmodel.SystemShortcutsViewModelFactory
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class SystemActivity : AppCompatActivity() {

    @Inject
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        SplitCompat.installActivity(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerSystemComponent.builder().appDependencies(
            EntryPointAccessors.fromApplication(
                this,
                SystemModuleDependencies::class.java
            )
        ).build().inject(this)
        super.onCreate(savedInstanceState)

        setContent {
            val initialRoute = intent.dataString ?: DEEPLINK_SYSTEM

            val navController = rememberNavController()

            AppTheme {
                NavHost(navController = navController, startDestination = initialRoute) {
                    composable(DEEPLINK_SYSTEM_DEEPLINK) {
                        val viewModel: SystemShortcutsViewModel = viewModel(
                            factory = SystemShortcutsViewModelFactory(
                                userPreferencesRepository = userPreferencesRepository,
                                featureId = ID_SYSTEM_DEEPLINK_NAVIGATOR,
                                moduleName = SYSTEM_MODULE_NAME
                            )
                        )
                        SystemDeeplinkScreen(
                            navigateBack = onBackPressedDispatcher::onBackPressed,
                            stateHolder = viewModel
                        )
                    }
                    composable(DEEPLINK_SYSTEM_INTENT) {
                        val viewModel: SystemShortcutsViewModel = viewModel(
                            factory = SystemShortcutsViewModelFactory(
                                userPreferencesRepository = userPreferencesRepository,
                                featureId = ID_SYSTEM_ACTION_NAVIGATOR,
                                moduleName = SYSTEM_MODULE_NAME
                            )
                        )
                        SystemIntentScreen(
                            navigateBack = onBackPressedDispatcher::onBackPressed,
                            stateHolder = viewModel
                        )
                    }
                    composable(DEEPLINK_SYSTEM) {
                        SystemLandingScreen(
                            navigateClose = onBackPressedDispatcher::onBackPressed,
                            navigateToDeeplinkScreen = {
                                navController.navigate(DEEPLINK_SYSTEM_DEEPLINK)
                            },
                            navigateToIntentScreen = {
                                navController.navigate(DEEPLINK_SYSTEM_INTENT)
                            }
                        )
                    }
                }
            }
        }
    }
}