package com.jmat.dashboard.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jmat.dashboard.data.model.Module
import com.jmat.dashboard.ui.component.Alert
import com.jmat.dashboard.ui.component.DashboardHeader
import com.jmat.dashboard.ui.component.ModuleItem
import com.jmat.dashboard.ui.component.ShortcutItem
import com.jmat.dashboard.ui.fixture.DashboardFixtures
import com.jmat.dashboard.ui.model.ModuleInstallData
import com.jmat.dashboard.ui.model.ModuleState
import com.jmat.dashboard.ui.model.ShortcutData
import com.jmat.powertools.R
import com.jmat.powertools.base.compose.theme.AppTheme
import com.jmat.powertools.base.extensions.navigateDeeplink
import com.jmat.powertools.modules.settings.DEEPLINK_SETTINGS

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DashboardScreen(
    shortcuts: List<ShortcutData>,
    moduleInstallData: List<ModuleInstallData>,
    loading: Boolean,
    installModule: (Module) -> Unit,
    uninstallModule: (Module) -> Unit
) {
    val context = LocalContext.current
    var uninstallAlert by remember { mutableStateOf<Module?>(null) }

    if (loading) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth()
        )
        return
    }

    Surface {
        uninstallAlert?.let { module ->
            Alert(
                title = stringResource(id = com.jmat.dashboard.R.string.dashboard_uninstall_dialog_title),
                text = stringResource(id = com.jmat.dashboard.R.string.dashboard_uninstall_dialog_message),
                confirmText = stringResource(id = com.jmat.dashboard.R.string.dashboard_uninstall_dialog_uninstall),
                dismissText = stringResource(id = com.jmat.dashboard.R.string.dashboard_uninstall_dialog_dismiss),
            ) { confirmed ->
                if (confirmed) {
                    uninstallModule(module)
                }
                uninstallAlert = null
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .background(
                    color = MaterialTheme.colorScheme.surface
                )
                .padding(vertical = dimensionResource(id = R.dimen.layout_padding)),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            if (shortcuts.isNotEmpty()) {
                item {
                    DashboardHeader(
                        modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.layout_padding)),
                        title = stringResource(id = com.jmat.dashboard.R.string.dashboard_title_shortcuts),
                        onSearchClicked = { }
                    )
                }
                composeShortcuts(context, shortcuts)
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            item {
                DashboardHeader(
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.layout_padding)),
                    title = stringResource(id = com.jmat.dashboard.R.string.dashboard_title_modules)
                )
            }
            items(moduleInstallData) {
                Card(
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.layout_padding))
                        .combinedClickable(
                            onClick = {
                                when (it.moduleState) {
                                    ModuleState.Installed -> {
                                        context.navigateDeeplink(it.module.entrypoint)
                                    }
                                    ModuleState.Installing -> {
                                        Toast
                                            .makeText(context, "Please wait", Toast.LENGTH_LONG)
                                            .show()
                                    }
                                    is ModuleState.Failed,
                                    ModuleState.Uninstalled -> {
                                        installModule(it.module)
                                    }
                                }
                            },
                            onLongClick = {
                                when (it.moduleState) {
                                    ModuleState.Installed -> {
                                        // Launch Confirmation
                                        uninstallAlert = it.module
                                    }
                                    else -> { /* no-op */
                                    }
                                }
                            }
                        )
                ) {
                    ModuleItem(
                        modifier = Modifier.padding(8.dp),
                        module = it.module,
                        moduleState = it.moduleState
                    )
                }
            }
            item {
                TextButton(
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.layout_padding)),
                    onClick = { context.navigateDeeplink(DEEPLINK_SETTINGS) }
                ) {
                    Text(
                        text = stringResource(id = R.string.title_settings)
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
private fun LazyListScope.composeShortcuts(
    context: Context,
    shortcuts: List<ShortcutData>,
) {
    item {
        LazyHorizontalGrid(
            modifier = Modifier
                .height(260.dp)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.layout_padding)),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            rows = GridCells.Adaptive(128.dp),
            content = {
                items(shortcuts) {
                    Card(onClick = {
                        context.navigateDeeplink(it.action)
                    }) {
                        ShortcutItem(
                            modifier = Modifier.padding(8.dp),
                            shortcutData = it
                        )
                    }
                }
            }
        )
    }
}

@Composable
@Preview
fun DashboardScreenLight() {
    AppTheme(darkTheme = false) {
        DashboardScreen(
            shortcuts = listOf(
                DashboardFixtures.shortcutData,
                DashboardFixtures.shortcutData,
                DashboardFixtures.shortcutData,
                DashboardFixtures.shortcutData,
            ),
            moduleInstallData = listOf(
                ModuleInstallData(
                    DashboardFixtures.module,
                    ModuleState.Installed
                )
            ),
            loading = true,
            installModule = { },
            uninstallModule = { }
        )
    }
}

@Composable
@Preview
fun DashboardScreenWithoutShortcuts() {
    AppTheme(darkTheme = true) {
        DashboardScreen(
            shortcuts = listOf(),
            moduleInstallData = listOf(
                ModuleInstallData(
                    DashboardFixtures.module,
                    ModuleState.Installed
                )
            ),
            loading = false,
            installModule = { },
            uninstallModule = { }
        )
    }
}

@Composable
@Preview
fun DashboardScreenDark() {
    AppTheme(darkTheme = true) {
        DashboardScreen(
            shortcuts = listOf(
                DashboardFixtures.shortcutData,
                DashboardFixtures.shortcutData,
                DashboardFixtures.shortcutData,
                DashboardFixtures.shortcutData,
                DashboardFixtures.shortcutData,
                DashboardFixtures.shortcutData,
                DashboardFixtures.shortcutData,
            ),
            moduleInstallData = listOf(
                ModuleInstallData(
                    DashboardFixtures.module,
                    ModuleState.Uninstalled
                )
            ),
            loading = false,
            installModule = { },
            uninstallModule = { }
        )
    }
}
