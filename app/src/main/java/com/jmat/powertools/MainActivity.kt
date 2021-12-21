package com.jmat.powertools

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.jmat.powertools.base.extensions.navigateDeeplink
import com.jmat.powertools.base.extensions.navigateLauncherActions
import com.jmat.powertools.modules.dashboard.DEEPLINK_DASHBOARD

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.textView).setOnClickListener {
            navigateDeeplink(DEEPLINK_DASHBOARD)
        }
    }
}