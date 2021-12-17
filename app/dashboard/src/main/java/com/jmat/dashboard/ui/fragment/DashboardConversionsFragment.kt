package com.jmat.dashboard.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.jmat.dashboard.R
import com.jmat.dashboard.databinding.FragmentDashboardConversionsBinding
import com.jmat.powertools.LauncherActions
import com.jmat.powertools.base.delegate.viewBinding
import com.jmat.powertools.base.extensions.navigateLauncherActions

class DashboardConversionsFragment : Fragment(R.layout.fragment_dashboard_conversions) {
    private val binding: FragmentDashboardConversionsBinding by viewBinding(FragmentDashboardConversionsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.kilometersToMiles.setOnClickListener {
            requireContext().navigateLauncherActions(
                launcherAction = LauncherActions.CONVERSIONS
            )
        }

        binding.l100kmToMPG.setOnClickListener {
            requireContext().navigateLauncherActions(
                launcherAction = LauncherActions.CONVERSIONS
            )
        }

        binding.millitersToOunces.setOnClickListener {
            requireContext().navigateLauncherActions(
                launcherAction = LauncherActions.CONVERSIONS
            )
        }
    }
}