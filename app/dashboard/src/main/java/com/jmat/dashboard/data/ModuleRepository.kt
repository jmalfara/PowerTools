package com.jmat.dashboard.data

import android.util.Log
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.jmat.dashboard.R
import com.jmat.dashboard.data.datastore.DashboardStoreService
import com.jmat.dashboard.data.model.Module
import com.jmat.dashboard.data.model.ModuleListings
import com.jmat.dashboard.data.model.Modules
import com.jmat.powertools.base.data.ImageDownloadService
import com.jmat.powertools.base.data.ResourceService
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class ModuleRepository @Inject constructor(
    private val resourceService: ResourceService,
    private val imageDownloadService: ImageDownloadService,
    private val splitInstallManager: SplitInstallManager,
    private val dashboardStoreService: DashboardStoreService
) {
    suspend fun downloadModules(): Result<Modules> {
        val result = resourceService.loadResource<Modules>(R.raw.modules).onSuccess { modulesData ->
            val images = modulesData.modules.map { it.iconUrl }
            imageDownloadService.downloadImages(images)
        }

        if (result.isSuccess) {
            dashboardStoreService.resetModules(result.getOrThrow().modules)
        }

        return result
    }

    suspend fun fetchModuleListings(): Result<ModuleListings> {
        return resourceService.loadResource<ModuleListings>(R.raw.store_listings)
            .onSuccess { moduleListings ->
                moduleListings.new
                    .forEach {
                        imageDownloadService.downloadImages(it.previewUrls)
                    }

                moduleListings.popular
                    .forEach {
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
            dashboardStoreService.addInstalledModule(module.installName)
        }

        return result
    }

    suspend fun uninstallModule(moduleName: String): Result<Unit> {
        val result = suspendCancellableCoroutine<Result<Unit>> { continuation ->
            splitInstallManager.deferredUninstall(listOf(moduleName))
                .addOnSuccessListener {
                    continuation.resume(Result.success(Unit))
                }
                .addOnFailureListener {
                    Log.e("Module", "Uninstall", it)
                    continuation.resume(Result.failure(it))
                }
        }

        if (result.isSuccess) {
            dashboardStoreService.removeInstalledModule(
                moduleName = moduleName
            )
        }

        return result
    }
}