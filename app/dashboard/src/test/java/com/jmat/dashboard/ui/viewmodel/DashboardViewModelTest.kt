package com.jmat.dashboard.ui.viewmodel

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.jmat.powertools.Favourite
import com.jmat.powertools.UserPreferences
import com.jmat.powertools.data.preferences.UserPreferencesRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DashboardViewModelTest {

    @Test
    fun `test that favourites emits when repo updates`() = runBlocking {
        val expectedFavourite = Favourite.getDefaultInstance()

        val userPreferences = UserPreferences.getDefaultInstance()
            .toBuilder()
            .addFavourites(expectedFavourite).build()
        val userPreferencesData: Flow<UserPreferences> = MutableStateFlow(userPreferences)

        val viewModel = DashboardViewModel(
            userPreferencesRepository = mockk<UserPreferencesRepository>().apply {
                every { data } returns userPreferencesData
            }
        )

        viewModel.favourites.test {
            val favourites = awaitItem()
            assertThat(favourites).hasSize(1)
            assertThat(favourites[0]).isEqualTo(expectedFavourite)
        }
    }
}