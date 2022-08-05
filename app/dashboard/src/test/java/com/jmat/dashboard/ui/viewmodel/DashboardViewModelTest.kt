package com.jmat.dashboard.ui.viewmodel

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.jmat.dashboard.data.ModuleRepository
import com.jmat.dashboard.data.model.Modules
import com.jmat.powertools.Favourite
import com.jmat.powertools.Feature
import com.jmat.powertools.InstalledModule
import com.jmat.powertools.Module
import com.jmat.powertools.UserPreferences
import com.jmat.powertools.data.preferences.UserPreferencesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class DashboardViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test that favourites emits when repo updates`() = runTest {
        val expectedFavourite = Favourite.getDefaultInstance()

        val userPreferences = UserPreferences.getDefaultInstance()
            .toBuilder()
            .addFavourites(expectedFavourite).build()
        val userPreferencesData: Flow<UserPreferences> = MutableStateFlow(userPreferences)

        val viewModel = DashboardViewModel(
            userPreferencesRepository = mockk<UserPreferencesRepository>().apply {
                every { data } returns userPreferencesData
            },
            moduleRepository = mockk()
        )

        viewModel.favourites.test {
            val favourites = awaitItem()
            assertThat(favourites).hasSize(1)
            assertThat(favourites[0]).isEqualTo(expectedFavourite)
        }
    }

    @Test
    fun `test uiState installedModules contains entry only when module is known`() = runTest {
        val moduleName = "ModuleName"
        val userPreferences = UserPreferences.getDefaultInstance()
            .toBuilder()
            .addModules(
                Module
                    .newBuilder()
                    .setInstallName(moduleName)
                    .build()
            )
            .addAllInstalledModules(
                listOf(
                    InstalledModule
                        .newBuilder()
                        .setModuleName(moduleName)
                        .build(),
                    InstalledModule
                        .newBuilder()
                        .setModuleName("OtherModule")
                        .build()
                )
            )
            .build()


        val userPreferencesData: Flow<UserPreferences> = MutableStateFlow(userPreferences)

        val viewModel = DashboardViewModel(
            userPreferencesRepository = mockk<UserPreferencesRepository>().apply {
                every { data } returns userPreferencesData
            },
            moduleRepository = mockk()
        )

        viewModel.uiState.test {
            val uiState = awaitItem()
            assertThat(uiState.installedModules).hasSize(1)
            assertThat(uiState.installedModules.first().installName).isEqualTo(moduleName)
        }
    }

    @Test
    fun `test uiState favourites contains entry only when module is known`() = runTest {
        val moduleName = "ModuleName"
        val featureId = "FavouriteId"
        val userPreferences = UserPreferences.getDefaultInstance()
            .toBuilder()
            .addModules(
                Module
                    .newBuilder()
                    .setInstallName(moduleName)
                    .addAllFeatures(
                        listOf(
                            Feature
                                .newBuilder()
                                .setId(featureId)
                                .build(),
                            Feature
                                .newBuilder()
                                .setId("OtherFeatureId")
                                .build()
                        )
                    )
                    .build()
            )
            .addAllFavourites(
                listOf(
                    Favourite
                        .newBuilder()
                        .setModuleName(moduleName)
                        .setFeatureId(featureId)
                        .build(),
                    Favourite
                        .newBuilder()
                        .setModuleName("OtherModule")
                        .build()
                )
            )
            .build()
        val userPreferencesData: Flow<UserPreferences> = MutableStateFlow(userPreferences)

        val viewModel = DashboardViewModel(
            userPreferencesRepository = mockk<UserPreferencesRepository>().apply {
                every { data } returns userPreferencesData
            },
            moduleRepository = mockk()
        )

        viewModel.uiState.test {
            val uiState = awaitItem()
            assertThat(uiState.favouriteFeatures).hasSize(1)
            with(uiState.favouriteFeatures.first()) {
                assertThat(id).isEqualTo(featureId)
                assertThat(moduleName).isEqualTo(moduleName)
            }
        }
    }

    @Test
    fun `test uiState downloadModuleCatalog automatically when modules are empty`() = runTest {
        val userPreferences = UserPreferences.getDefaultInstance()
            .toBuilder()
            .addAllModules(listOf()) // Empty Modules
            .build()
        val userPreferencesData: Flow<UserPreferences> = MutableStateFlow(userPreferences)

        val moduleRepository = mockk<ModuleRepository>().apply {
            coEvery { downloadModules() } returns Result.success(Modules(listOf()))
        }

        DashboardViewModel(
            userPreferencesRepository = mockk<UserPreferencesRepository>().apply {
                every { data } returns userPreferencesData
            },
            moduleRepository = moduleRepository
        )

        coVerify(exactly = 1) { moduleRepository.downloadModules() }
    }

    @Test
    fun `test uiState downloadModuleCatalog manual request`() = runTest {
        val userPreferences = UserPreferences.getDefaultInstance()
            .toBuilder()
            .addModules(
                Module
                    .newBuilder()
                    .setInstallName("moduleName")
                    .build()
            ) // Empty Modules
            .build()
        val userPreferencesData: Flow<UserPreferences> = MutableStateFlow(userPreferences)

        val moduleRepository = mockk<ModuleRepository>().apply {
            coEvery { downloadModules() } returns Result.success(Modules(listOf()))
        }

        val viewModel = DashboardViewModel(
            userPreferencesRepository = mockk<UserPreferencesRepository>().apply {
                every { data } returns userPreferencesData
            },
            moduleRepository = moduleRepository
        )

        viewModel.uiState.test {
            viewModel.downloadModuleCatalog()
            with(awaitItem()) {
                assertThat(loading).isFalse()
            }

            with(awaitItem()) {
                assertThat(loading).isTrue()
            }

            with(awaitItem()) {
                assertThat(loading).isFalse()
            }
        }

        coVerify(exactly = 1) { moduleRepository.downloadModules() }
    }
}

class MainDispatcherRule @OptIn(ExperimentalCoroutinesApi::class) constructor(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}