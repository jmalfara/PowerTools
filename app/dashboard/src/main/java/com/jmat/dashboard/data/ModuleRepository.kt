package com.jmat.dashboard.data

import android.util.Log
import androidx.datastore.core.DataStore
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.jmat.dashboard.R
import com.jmat.dashboard.data.model.ModuleListings
import com.jmat.dashboard.ui.util.SplitInstallManagerDelegate
import com.jmat.powertools.Module
import com.jmat.powertools.UserPreferences
import com.jmat.powertools.base.data.ImageDownloadService
import com.jmat.powertools.base.data.ResourceService
import com.jmat.powertools.base.extensions.findIndex
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class ModuleRepository @Inject constructor(
    private val resourceService: ResourceService,
    private val imageDownloadService: ImageDownloadService,
    private val splitInstallManager: SplitInstallManager,
    private val dataStore: DataStore<UserPreferences>
) {
    suspend fun fetchModules(): Result<ModuleListings> {
        return resourceService.loadResource<ModuleListings>(R.raw.store_listings)
            .onSuccess { moduleListings ->
                moduleListings.newModules
                    .forEach {
                        imageDownloadService.downloadImages(listOf(it.iconUrl))
                        imageDownloadService.downloadImages(it.previewUrls)
                    }

                moduleListings.popularModules
                    .forEach {
                        imageDownloadService.downloadImages(listOf(it.iconUrl))
                        imageDownloadService.downloadImages(it.previewUrls)
                    }
            }
    }

    suspend fun installModule(module: String): Result<Unit> {
        val request = SplitInstallRequest.newBuilder()
            .addModule(module)
            .build()

        val result = suspendCancellableCoroutine<Result<Unit>> { continuation ->
            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    continuation.resume(Result.success(Unit))
                }
                .addOnFailureListener {
                    Log.e("Module", "Install", it)
                    continuation.resume(Result.failure(it))
                }
        }

        if (result.isSuccess) {
            dataStore.updateData { preferences ->
                preferences.toBuilder()
                    .addModules(
                        Module.getDefaultInstance()
                            .toBuilder()
                            .setInstallName(module)
                            .build()
                    )
                    .build()
            }
        }

        return result
    }

    suspend fun uninstallModule(module: String): Result<Unit> {
        val result = suspendCancellableCoroutine<Result<Unit>> { continuation ->
            splitInstallManager.deferredUninstall(listOf(module))
                .addOnSuccessListener {
                    continuation.resume(Result.success(Unit))
                }
                .addOnFailureListener {
                    Log.e("Module", "Uninstall", it)
                    continuation.resume(Result.failure(it))
                }
        }

        if (result.isSuccess) {
            dataStore.updateData { preferences ->
                val index = preferences.modulesList.findIndex { it.installName == module }
                preferences.toBuilder()
                    .removeModules(index)
                    .build()
            }
        }

        return result
    }
}