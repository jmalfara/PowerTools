package com.jmat.dashboard.ui.fixture

import androidx.compose.material3.SnackbarHostState
import com.jmat.dashboard.data.model.Module
import com.jmat.dashboard.ui.model.ModuleInstallData
import com.jmat.dashboard.ui.model.ModuleState
import com.jmat.dashboard.ui.model.ShortcutData
import com.jmat.dashboard.ui.stateholders.DashboardStateHolder

object DashboardFixtures {
    val module = Module(
        name = "Name",
        author = "Author",
        iconUrl = "IconUrl",
        shortDescription = "ShortDescription",
        installName = "InstallName",
        entrypoint = "Entrypoint",
        features = listOf()
    )

    val shortcutData = ShortcutData(
        id = "1234",
        name = "Lorem Ipsum is simply dummy text",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
        icon = "URL",
        action = "action"
    )

    val dashboardStateHolder = DashboardStateHolderFixture(
        loading = false,
        moduleInstallData = listOf(
            ModuleInstallData(
                module,
                ModuleState.Installed
            ),
        ),
        shortcutData = listOf(
            shortcutData,
            shortcutData,
            shortcutData,
            shortcutData
        ),
        showUninstallAlert = false,
    )
}

data class DashboardStateHolderFixture(
    override val loading: Boolean,
    override val moduleInstallData: List<ModuleInstallData>,
    override val shortcutData: List<ShortcutData>,
    override val showUninstallAlert: Boolean,
    override val snackbarHostState: SnackbarHostState = SnackbarHostState()
) : DashboardStateHolder {
    override fun requireUninstallModule(): Module { return DashboardFixtures.module }
    override fun consumeUninstallModule() {}
    override fun requestUninstallModule(module: Module) {}
    override fun confirmUninstallModule(module: Module) {}
    override fun confirmInstallModule(module: Module) {}
}