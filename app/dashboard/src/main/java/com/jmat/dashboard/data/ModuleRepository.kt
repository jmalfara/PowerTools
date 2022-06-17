package com.jmat.dashboard.data

import android.util.Log
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.jmat.dashboard.R
import com.jmat.dashboard.data.model.Module
import com.jmat.dashboard.data.model.ModuleListings
import com.jmat.powertools.base.data.ImageDownloadService
import com.jmat.powertools.base.data.ResourceService
import com.jmat.powertools.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class ModuleRepository @Inject constructor(
    private val resourceService: ResourceService,
    private val imageDownloadService: ImageDownloadService,
    private val splitInstallManager: SplitInstallManager,
    private val userPreferencesRepo: UserPreferencesRepository
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

    suspend fun installModule(module: Module): Result<Unit> {
        val request = SplitInstallRequest.newBuilder()
            .addModule(module.installName)
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
            userPreferencesRepo.addModule(
                id = module.id,
                name = module.name,
                author = module.author,
                iconUrl = module.iconUrl,
                shortDescription = module.shortDescription,
                previewUrls = module.previewUrls,
                previewType = module.previewType,
                installName = module.installName,
                entrypoint = module.entrypoint
            )
        }

        return result
    }

    suspend fun uninstallModule(moduleInstallName: String): Result<Unit> {
        val result = suspendCancellableCoroutine<Result<Unit>> { continuation ->
            splitInstallManager.deferredUninstall(listOf(moduleInstallName))
                .addOnSuccessListener {
                    continuation.resume(Result.success(Unit))
                }
                .addOnFailureListener {
                    Log.e("Module", "Uninstall", it)
                    continuation.resume(Result.failure(it))
                }
        }

        if (result.isSuccess) {
            userPreferencesRepo.removeModuleByInstallName(
                installName = moduleInstallName
            )
        }

        return result
    }
}