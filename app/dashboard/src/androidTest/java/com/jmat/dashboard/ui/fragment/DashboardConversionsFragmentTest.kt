package com.jmat.dashboard.ui.fragment

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jmat.dashboard.R
import com.jmat.powertools.base.extensions.navigateDeeplink
import com.jmat.powertools.modules.conversions.DEEPLINK_CONVERSIONS_KM_TO_MILES
import com.jmat.powertools.modules.conversions.DEEPLINK_CONVERSIONS_L100KM_TO_MPG
import com.jmat.powertools.modules.conversions.DEEPLINK_CONVERSIONS_ML_TO_OUNCES
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardConversionsFragmentTest {

    @Before
    fun setup() {
        mockkStatic("com.jmat.powertools.base.extensions.ContextExtensionsKt")
        every { any<Context>().navigateDeeplink(any()) } returns Unit
    }

    @Test
    fun testDashboardConversionsFragment_kmToMiles_navigation() {
        val scenario = launchFragmentInContainer<DashboardConversionsFragment>(
            themeResId = com.jmat.powertools.R.style.Theme_App
        )

        onView(withId(R.id.kilometersToMiles)).perform(click())

        scenario.onFragment { fragment ->
            verify { fragment.requireContext().navigateDeeplink(DEEPLINK_CONVERSIONS_KM_TO_MILES) }
        }
    }

    @Test
    fun testDashboardConversionsFragment_l100kmToMPG_navigation() {
        val scenario = launchFragmentInContainer<DashboardConversionsFragment>(
            themeResId = com.jmat.powertools.R.style.Theme_App
        )

        onView(withId(R.id.l100kmToMPG)).perform(click())

        scenario.onFragment { fragment ->
            verify { fragment.requireContext().navigateDeeplink(DEEPLINK_CONVERSIONS_L100KM_TO_MPG) }
        }
    }

    @Test
    fun testDashboardConversionsFragment_millitersToOunces_navigation() {
        val scenario = launchFragmentInContainer<DashboardConversionsFragment>(
            themeResId = com.jmat.powertools.R.style.Theme_App
        )

        onView(withId(R.id.millitersToOunces)).perform(click())

        scenario.onFragment { fragment ->
            verify { fragment.requireContext().navigateDeeplink(DEEPLINK_CONVERSIONS_ML_TO_OUNCES) }
        }
    }
}