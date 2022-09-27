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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class ModuleRepository constructor(
    private val resourceService: ResourceService,
    private val imageDownloadService: ImageDownloadService,
    private val splitInstallManager: SplitInstallManager,
    coroutineScope: CoroutineScope
) {
    private val _moduleInstallData = MutableStateFlow<List<ModuleInstallData>>(listOf())
    val moduleInstallData: StateFlow<List<ModuleInstallData>> = _moduleInstallData

    private val _installQueue = MutableStateFlow(listOf<Module>())
    private val _installItem = _installQueue.transform {
        emit(it.firstOrNull())
    }.distinctUntilChanged().filterNotNull()

    init {
        coroutineScope.launch {
            _installItem.collect { module ->
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
                    updateModuleInstallState(
                        ModuleState.Failed(result.exceptionOrNull().toString()),
                        module.installName
                    )
                }

                _installQueue.emit(
                    mutableListOf<Module>().apply {
                        addAll(_installQueue.value)
                        removeFirstOrNull()
                    }
                )
            }
        }

//        splitInstallManager.sessionStates.addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                task.result.forEach { state ->
//                    when (state.status()) {
//                        UNKNOWN -> {
//                            Log.d("ModuleRepository", state.moduleNames().toString() + " UNKNOWN")
//                        }
//                        PENDING -> {
//                            Log.d("ModuleRepository", state.moduleNames().toString() + " PENDING")
//                        }
//                        DOWNLOADING -> {
//                            Log.d(
//                                "ModuleRepository",
//                                state.moduleNames().toString() + " DOWNLOADING"
//                            )
//                        }
//                        DOWNLOADED -> {
//                            Log.d(
//                                "ModuleRepository",
//                                state.moduleNames().toString() + " DOWNLOADED"
//                            )
//                        }
//                        REQUIRES_USER_CONFIRMATION -> {
//                            Log.d(
//                                "ModuleRepository",
//                                state.moduleNames().toString() + " REQUIRES_USER_CONFIRMATION"
//                            )
//                        }
//                        INSTALLING -> {
//                            Log.d(
//                                "ModuleRepository",
//                                state.moduleNames().toString() + " INSTALLING"
//                            )
//                        }
//                        INSTALLED -> {
//                            Log.d("ModuleRepository", state.moduleNames().toString() + " Installed")
//                        }
//                        FAILED -> {
//                            Log.d("ModuleRepository", state.moduleNames().toString() + " FAILED")
//                        }
//                        CANCELING -> {
//                            Log.d("ModuleRepository", state.moduleNames().toString() + " CANCELING")
//                        }
//                        CANCELED -> {
//                            Log.d("ModuleRepository", state.moduleNames().toString() + " CANCELED")
//                        }
//                    }
//                }
//            }
//        }.addOnFailureListener {
//            Log.d("ModuleRepository", "$it UNKNOWN")
//        }
    }

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

        _installQueue.emit(
            mutableListOf<Module>().apply {
                addAll(_installQueue.value)
                add(module)
            }
        )

        return Result.success(Unit)
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