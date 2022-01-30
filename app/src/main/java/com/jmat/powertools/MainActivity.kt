package com.jmat.powertools

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.jmat.powertools.base.extensions.navigateDeeplink
import com.jmat.powertools.modules.dashboard.DEEPLINK_DASHBOARD

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        setContentView(R.layout.activity_main)

        splashScreen.setOnExitAnimationListener {
            navigateDeeplink(DEEPLINK_DASHBOARD)
        }
    }
}