package com.jmat.powertools

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.jmat.powertools.base.extensions.navigateDeeplink
import com.jmat.powertools.modules.dashboard.DEEPLINK_DASHBOARD
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            delay(2000)
            navigateDeeplink(DEEPLINK_DASHBOARD)
        }
    }
}
