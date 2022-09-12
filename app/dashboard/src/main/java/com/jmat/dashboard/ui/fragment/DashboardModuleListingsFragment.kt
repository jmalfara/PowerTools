package com.jmat.dashboard.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jmat.dashboard.R
import com.jmat.dashboard.databinding.FragmentDashboardListingsBinding
import com.jmat.dashboard.ui.adapter.DashboardModuleListingsViewPagerAdapter
import com.jmat.powertools.base.delegate.viewBinding

class DashboardModuleListingsFragment : Fragment(R.layout.fragment_dashboard_listings) {
    private val binding: FragmentDashboardListingsBinding by viewBinding(
        FragmentDashboardListingsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            viewPager.adapter = DashboardModuleListingsViewPagerAdapter(
                fragmentManager = childFragmentManager,
                lifecycle = viewLifecycleOwner.lifecycle
            )
        }
    }
}