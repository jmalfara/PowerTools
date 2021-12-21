package com.jmat.dashboard.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.jmat.dashboard.R
import com.jmat.dashboard.databinding.FragmentDashboardConversionsBinding
import com.jmat.powertools.DeeplinkActions
import com.jmat.powertools.LauncherActions
import com.jmat.powertools.base.delegate.viewBinding
import com.jmat.powertools.base.extensions.navigateDeeplink
import com.jmat.powertools.base.extensions.navigateLauncherActions
import com.jmat.powertools.modules.conversions.CONVERSIONS_ACTION_KEY
import com.jmat.powertools.modules.conversions.CONVERSIONS_ACTION_KM_TO_MILES
import com.jmat.powertools.modules.conversions.CONVERSIONS_ACTION_KM_TO_MPG
import com.jmat.powertools.modules.conversions.CONVERSIONS_ACTION_MILLILITERS_TO_OUNCES

class DashboardConversionsFragment : Fragment(R.layout.fragment_dashboard_conversions) {
    private val binding: FragmentDashboardConversionsBinding by viewBinding(FragmentDashboardConversionsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.kilometersToMiles.setOnClickListener {
            requireContext().navigateDeeplink(
                DeeplinkActions.CONVERSIONS_KM_TO_MILES
            )
        }

        binding.l100kmToMPG.setOnClickListener {
            requireContext().navigateDeeplink(
                DeeplinkActions.CONVERSIONS_L100KM_TO_MPG
            )
        }

        binding.millitersToOunces.setOnClickListener {
            requireContext().navigateDeeplink(
                DeeplinkActions.CONVERSIONS_ML_TO_OUNCES
            )
        }
    }
}