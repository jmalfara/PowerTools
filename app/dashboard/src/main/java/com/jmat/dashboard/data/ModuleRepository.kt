package com.jmat.dashboard.data

import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.jmat.dashboard.R
import com.jmat.dashboard.data.model.Module
import com.jmat.dashboard.data.model.Modules
import com.jmat.dashboard.ui.model.ModuleInstallData
import com.jmat.dashboard.ui.model.ModuleState
import com.jmat.powertools.base.data.ImageDownloadService
import com.jmat.powertools.base.data.ResourceService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class ModuleRepository constructor(
    private val resourceService: ResourceService,
    private val imageDownloadService: ImageDownloadService,
    private val splitInstallManager: SplitInstallManager
) {
    private val _moduleInstallData = MutableStateFlow<List<ModuleInstallData>>(listOf())
    val moduleInstallData: StateFlow<List<ModuleInstallData>> = _moduleInstallData

    suspend fun downloadModuleInstallData(): Result<List<ModuleInstallData>> {
        val result = resourceService.loadResource<Modules>(R.raw.modules).map { moduleData ->
            val images = moduleData.modules.map { it.iconUrl }
            imageDownloadService.downloadImages(images) //Cache the image with Glide
            moduleData.modules.map {
                val installed = splitInstallManager.installedModules.contains(it.installName)
                ModuleInstallData(
                    moduleState = if (installed) {
                        ModuleState.Installed
                    } else ModuleState.Uninstalled,
                    module = it
                )
            }
        }.onSuccess {
            _moduleInstallData.emit(it)
        }

        return result
    }

    suspend fun installModule(module: Module): Result<Unit> {
        updateModuleInstallState(ModuleState.Installing, module.installName)

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
            updateModuleInstallState(ModuleState.Installed, module.installName)
        } else {
            updateModuleInstallState(ModuleState.Uninstalled, module.installName)
        }

        return result
    }

    suspend fun uninstallModule(moduleName: String): Result<Unit> {
        updateModuleInstallState(ModuleState.Installing, moduleName)

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
            updateModuleInstallState(ModuleState.Uninstalled, moduleName)
        } else {
            updateModuleInstallState(ModuleState.Installed, moduleName)
        }

        return result
    }

    private suspend fun updateModuleInstallState(moduleState: ModuleState, installName: String) {
        val data = _moduleInstallData.value.map {
            if (it.module.installName == installName) {
                it.copy(
                    moduleState = moduleState
                )
            } else it
        }
        _moduleInstallData.emit(data)
    }
}