package com.jmat.powertools

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.jmat.powertools.base.extensions.navigateLauncherActions

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.textView).setOnClickListener {
            navigateLauncherActions(
                launcherAction = LauncherActions.DASHBOARD
            )
        }
    }
}