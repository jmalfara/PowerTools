package com.jmat.system.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import com.jmat.powertools.base.compose.theme.AppTheme
import com.jmat.powertools.modules.system.DEEPLINK_SYSTEM
import com.jmat.powertools.modules.system.DEEPLINK_SYSTEM_INTENT
import com.jmat.powertools.modules.system.DEEPLINK_SYSTEM_DEEPLINK
import com.jmat.system.ui.router.rememberNavigationStack
import com.jmat.system.ui.screen.SystemIntentScreen
import com.jmat.system.ui.screen.SystemDeeplinkScreen
import com.jmat.system.ui.screen.SystemLandingScreen

class SystemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val initialRoute = intent.dataString ?: DEEPLINK_SYSTEM
            val routerStack = rememberNavigationStack(initialRoute, ::finish)
            val route = routerStack.currentRoute.collectAsState()

            AppTheme {
                when(route.value) {
                    DEEPLINK_SYSTEM_DEEPLINK -> SystemDeeplinkScreen(routerStack)
                    DEEPLINK_SYSTEM_INTENT -> SystemIntentScreen(routerStack)
                    else -> SystemLandingScreen(routerStack)
                }
            }
        }
    }
}