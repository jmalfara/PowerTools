package com.jmat.powertools.base.extensions

import android.content.ComponentName
import android.content.Context
import android.content.Intent
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

    val resolveInfo = packageManager.queryIntentActivities(intent, 0).firstOrNull() ?: return

    intent.component = ComponentName(
        packageName,
        resolveInfo.activityInfo.name
    )
    startActivity(intent)
}

fun Context.navigateDeeplink(
    deeplink: String
) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(deeplink))
    intent.setPackage(packageName)

    val resolveInfo = packageManager.queryIntentActivities(intent, 0).firstOrNull() ?: return

    intent.component = ComponentName(
        packageName,
        resolveInfo.activityInfo.name
    )
    startActivity(intent)
}
