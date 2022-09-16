package com.jmat.powertools.base.extensions

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import java.io.Serializable

fun Context.navigateLauncherActions(
    launcherAction: String,
    extras: Map<String, Serializable>? = null
) {
    val intent = Intent(launcherAction)
    intent.action = launcherAction
    extras?.forEach { item -> intent.putExtra(item.key, item.value) }
    intent.setPackage(packageName)
    startActivity(intent)
}

fun Context.navigateDeeplink(
    deeplink: String
) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(deeplink))
    intent.setPackage(packageName)
    val options = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
    intent.setClassName(packageName, options[0].activityInfo.name)
    startActivity(intent)
}
