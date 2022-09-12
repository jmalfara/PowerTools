package com.jmat.dashboard.data

import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.jmat.dashboard.R
import com.jmat.dashboard.data.datastore.DashboardStoreService
import com.jmat.dashboard.data.model.ModuleInstallData
import com.jmat.dashboard.data.model.Module
import com.jmat.dashboard.data.model.Modules
import com.jmat.powertools.base.data.ImageDownloadService
import com.jmat.powertools.base.data.ResourceService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class ModuleRepository @Inject constructor(
    private val resourceService: ResourceService,
    private val imageDownloadService: ImageDownloadService,
    private val splitInstallManager: SplitInstallManager,
    private val dashboardStoreService: DashboardStoreService
) {
    private val _moduleInstallData = MutableStateFlow<List<ModuleInstallData>>(listOf())
    val moduleInstallData: StateFlow<List<ModuleInstallData>> = _moduleInstallData

    suspend fun downloadModuleInstallData(): Result<List<ModuleInstallData>> {
        val result = resourceService.loadResource<Modules>(R.raw.modules).map { moduleData ->
            val images = moduleData.modules.map { it.iconUrl }
            imageDownloadService.downloadImages(images) //Cache the image with Glide
            moduleData.modules.map {
                ModuleInstallData(
                    installed = splitInstallManager.installedModules.contains(it.installName),
                    module = it
                )
            }
        }.onSuccess {
            _moduleInstallData.emit(it)
        }

        return result
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