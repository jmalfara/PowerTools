package com.jmat.dashboard.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jmat.dashboard.R
import com.jmat.dashboard.data.model.ModuleInstallData
import com.jmat.dashboard.data.model.Module
import com.jmat.dashboard.di.DaggerDashboardComponent
import com.jmat.dashboard.ui.compose.DashboardHeader
import com.jmat.dashboard.ui.compose.ModuleItem
import com.jmat.dashboard.ui.compose.ShortcutItem
import com.jmat.dashboard.ui.model.ShortcutData
import com.jmat.dashboard.ui.viewmodel.DashboardViewModel
import com.jmat.powertools.base.compose.theme.AppTheme
import com.jmat.powertools.base.di.InjectedViewModelFactory
import com.jmat.powertools.base.extensions.navigateDeeplink
import com.jmat.powertools.modules.dashboard.DashboardModuleDependencies
import com.jmat.powertools.modules.settings.DEEPLINK_SETTINGS
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class DashboardFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: InjectedViewModelFactory
    private val viewModel: DashboardViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerDashboardComponent.builder()
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireContext(),
                    DashboardModuleDependencies::class.java
                )
            ).build().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val uiState = viewModel.uiState.collectAsState(initial = DashboardViewModel.UiState.default)

                AppTheme {
                    DashboardScreen(
                        shortcuts = uiState.value.shortcutFeatures,
                        moduleInstallData = uiState.value.moduleInstallData,
                        loading = uiState.value.loading,
                        navigateToDetails = { module ->
                            findNavController().navigate(
                                DashboardFragmentDirections.dashboardToModuleDetails(
                                    module = module,
                                    installed = false
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun DashboardScreenLight() {
    AppTheme(darkTheme = false) {
        DashboardScreen(
            shortcuts = listOf(
                ShortcutData(
                    id = "1234",
                    name = "Name",
                    icon = "URL",
                    action = "action"
                ),
                ShortcutData(
                    id = "1234",
                    name = "Name",
                    icon = "URL",
                    action = "action"
                ),
                ShortcutData(
                    id = "1234",
                    name = "Name",
                    icon = "URL",
                    action = "action"
                ),
                ShortcutData(
                    id = "1234",
                    name = "Name",
                    icon = "URL",
                    action = "action"
                )
            ),
            moduleInstallData = listOf(
                ModuleInstallData(
                    Module(
                        name = "Name",
                        author = "Author",
                        iconUrl = "IconUrl",
                        shortDescription = "ShortDescription",
                        installName = "InstallName",
                        entrypoint = "Entrypoint",
                        features = listOf()
                    ),
                    true
                )
            ),
            loading = true,
            navigateToDetails = { }
        )
    }
}

@Composable
@Preview
fun DashboardScreenDark() {
    AppTheme(darkTheme = true) {
        DashboardScreen(
            shortcuts = listOf(
                ShortcutData(
                    id = "1234",
                    name = "Name",
                    icon = "URL",
                    action = "action"
                ),
                ShortcutData(
                    id = "1234",
                    name = "Name",
                    icon = "URL",
                    action = "action"
                ),
                ShortcutData(
                    id = "1234",
                    name = "Name",
                    icon = "URL",
                    action = "action"
                ),
                ShortcutData(
                    id = "1234",
                    name = "Name",
                    icon = "URL",
                    action = "action"
                )
            ),
            moduleInstallData = listOf(
                ModuleInstallData(
                    Module(
                        name = "Name",
                        author = "Author",
                        iconUrl = "IconUrl",
                        shortDescription = "ShortDescription",
                        installName = "InstallName",
                        entrypoint = "Entrypoint",
                        features = listOf()
                    ),
                    true
                )
            ),
            loading = false,
            navigateToDetails = { }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    shortcuts: List<ShortcutData>,
    moduleInstallData: List<ModuleInstallData>,
    loading: Boolean,
    navigateToDetails: (Module) -> Unit
) {
    val context = LocalContext.current

    if (loading) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth()
        )
        return
    }

    Surface {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .background(
                    color = MaterialTheme.colorScheme.surface
                )
                .padding(all = dimensionResource(id = com.jmat.powertools.R.dimen.layout_padding)),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            item {
                DashboardHeader(
                    title = stringResource(id = R.string.dashboard_title_shortcuts),
                    onSearchClicked = {  }
                )
            }
            item {
                LazyHorizontalGrid(
                    modifier = Modifier.height(128.dp),
                    rows = GridCells.Adaptive(64.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    content = {
                        items(shortcuts) {
                            Card (onClick = {
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
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                DashboardHeader(
                    title = stringResource(id = R.string.dashboard_title_modules)
                )
            }
            items(moduleInstallData) {
                Card(
                    onClick = {
                        if (it.installed) {
                            context.navigateDeeplink(it.module.entrypoint)
                        } else {
                            navigateToDetails(it.module)
                        }
                    }
                ) {
                    ModuleItem(
                        modifier = Modifier.padding(8.dp),
                        module = it.module,
                        installed = it.installed
                    )
                }
            }
            item {
                TextButton(
                    onClick = { context.navigateDeeplink(DEEPLINK_SETTINGS) }
                ) {
                    Text(
                        text = stringResource(id = com.jmat.powertools.R.string.title_settings)
                    )
                }
            }
        }
    }
}