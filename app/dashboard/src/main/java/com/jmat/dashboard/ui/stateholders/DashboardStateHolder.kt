package com.jmat.dashboard.ui.stateholders

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.jmat.dashboard.data.model.Module
import com.jmat.dashboard.ui.model.ModuleInstallData
import com.jmat.dashboard.ui.model.ShortcutData
import com.jmat.dashboard.ui.viewmodel.DashboardViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface DashboardStateHolder {
    val loading: Boolean
    val moduleInstallData: List<ModuleInstallData>
    val shortcutData: List<ShortcutData>
    val snackbarHostState: SnackbarHostState

    val showUninstallAlert: Boolean
    fun requireUninstallModule(): Module
    fun consumeUninstallModule()
    fun requestUninstallModule(module: Module)

    fun confirmUninstallModule(module: Module)
    fun confirmInstallModule(module: Module)
}

@Stable
class DashboardStateHolderImpl(
    uiState: DashboardViewModel.UiState,
    private val uninstallAlert: Module?,
    private val setUninstallModule: (Module?) -> Unit,
    private val installModule: (Module) -> Unit,
    private val uninstallModule: (Module) -> Unit,
    override val snackbarHostState: SnackbarHostState
) : DashboardStateHolder {
    override val loading: Boolean = uiState.loading
    override val moduleInstallData: List<ModuleInstallData> = uiState.moduleInstallData
    override val shortcutData: List<ShortcutData> = uiState.shortcutFeatures

    override val showUninstallAlert = uninstallAlert != null
    override fun requireUninstallModule(): Module = uninstallAlert!!
    override fun consumeUninstallModule() = setUninstallModule(null)
    override fun requestUninstallModule(module: Module) = setUninstallModule(module)

    override fun confirmUninstallModule(module: Module) = uninstallModule(module)
    override fun confirmInstallModule(module: Module) = installModule(module)
}

@Composable
fun rememberDashboardState(
    viewModel: DashboardViewModel
): DashboardStateHolder {
    var uninstallAlert by remember { mutableStateOf<Module?>(null) }
    val uiState = viewModel.uiState.collectAsState(initial = DashboardViewModel.UiState.default)
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    uiState.value.notificationText?.let {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                message = it,
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
        }
        viewModel.consumeNotificationText()
    }

    return remember(uiState.value, uninstallAlert) {
        DashboardStateHolderImpl(
//            navController = navController,
//            resources = resources,
            uiState = uiState.value,
            installModule = viewModel::installModule,
            uninstallModule = viewModel::uninstallModule,
            uninstallAlert = uninstallAlert,
            setUninstallModule = { uninstallAlert = it },
            snackbarHostState = snackbarHostState
        )
    }
}
