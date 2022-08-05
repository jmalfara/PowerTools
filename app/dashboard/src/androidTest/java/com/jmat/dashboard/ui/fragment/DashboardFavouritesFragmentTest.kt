package com.jmat.dashboard.ui.fragment

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jmat.powertools.base.extensions.navigateDeeplink
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardFavouritesFragmentTest {

    @Before
    fun setup() {
        mockkStatic("com.jmat.dashboard.di.DashboardDiExtensionsKt")
//        every { any<DashboardFavouritesFragment>().inject() } answers {
//            // Block the Injection
//        }

        mockkStatic("com.jmat.powertools.base.extensions.ContextExtensionsKt")
        every { any<Context>().navigateDeeplink(any()) } returns Unit
    }

//    @Test
//    fun test_DashboardFavouritesFragment_empty_state() {
//        val viewModel = DashboardViewModel(
//            userPreferencesRepository = mockk<UserPreferencesRepository>().apply {
//                every { data } returns flowOf(UserPreferences.getDefaultInstance())
//            }
//        )
//
//        launchFragmentInContainer(
//            themeResId = com.jmat.powertools.R.style.Theme_App
//        ) {
//            DashboardFavouritesFragment().apply {
//                viewModelFactory = mockk<InjectedViewModelFactory>().apply {
//                    every { create(DashboardViewModel::class.java) } returns viewModel
//                }
//            }
//        }
//        onView(withText(R.string.dashboard_empty_content)).perform(click())
//    }

//    @Test
//    fun test_DashboardFavouritesFragment_list_of_favourites() {
//        val deeplink = "powertools://deeplink"
//        val viewModel = DashboardViewModel(
//            userPreferencesRepository = mockk<UserPreferencesRepository>().apply {
//                every { data } returns flowOf(
//                    UserPreferences.getDefaultInstance()
//                        .toBuilder()
//                        .addFavourites(
//                            Favourite.newBuilder()
//                                .setDeeplink(deeplink)
//                                .setId(ID_CONVERSIONS_KM_TO_MILES)
//                                .build()
//                        )
//                        .addFavourites(
//                            Favourite.newBuilder()
//                                .setDeeplink(deeplink)
//                                .setId(ID_CONVERSIONS_ML_TO_OUNCES)
//                                .build()
//                        )
//                        .addFavourites(
//                            Favourite.newBuilder()
//                                .setDeeplink(deeplink)
//                                .setId(ID_CONVERSIONS_L100KM_TO_MPG)
//                                .build()
//                        )
//                        .build()
//                )
//            }
//        )
//
//        val scenario = launchFragmentInContainer(
//            themeResId = com.jmat.powertools.R.style.Theme_App
//        ) {
//            DashboardFavouritesFragment().apply {
//                viewModelFactory = mockk<InjectedViewModelFactory>().apply {
//                    every { create(DashboardViewModel::class.java) } returns viewModel
//                }
//            }
//        }
//
//        onView(withText(R.string.dashboard_conversion_title_km_to_m)).perform(click())
//        onView(withText(R.string.dashboard_conversion_title_ml_to_oz)).perform(click())
//        onView(withText(R.string.dashboard_conversion_title_l100km_to_mpg)).perform(click())
//
//        scenario.onFragment { fragment ->
//            verify(exactly = 3) { fragment.requireContext().navigateDeeplink(deeplink) }
//        }
//    }
}