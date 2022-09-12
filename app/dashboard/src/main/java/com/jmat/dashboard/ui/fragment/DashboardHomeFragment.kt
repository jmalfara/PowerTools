package com.jmat.dashboard.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jmat.dashboard.R
import com.jmat.dashboard.databinding.FragmentDashboardPagerBinding
import com.jmat.dashboard.ui.adapter.DashboardLandingViewPagerAdapter
import com.jmat.powertools.base.delegate.viewBinding

class DashboardHomeFragment : Fragment(R.layout.fragment_dashboard_pager) {
    private val binding: FragmentDashboardPagerBinding by viewBinding(
        FragmentDashboardPagerBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
//            val tabLayout = requireActivity().findViewById<TabLayout>(R.id.tabs)

            viewPager.adapter = DashboardLandingViewPagerAdapter(
                fragmentManager = childFragmentManager,
                lifecycle = viewLifecycleOwner.lifecycle
            )

//            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//                tab.text = when (position) {
//                    0 -> getString(R.string.tab_favourites)
//                    1 -> getString(R.string.tab_installed)
//                    else -> throw RuntimeException("Tab does not exist")
//                }
//            }.attach()
        }
    }
}