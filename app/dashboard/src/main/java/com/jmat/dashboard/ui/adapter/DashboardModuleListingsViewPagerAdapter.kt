package com.jmat.dashboard.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jmat.dashboard.ui.fragment.DashboardFavouritesFragment
import com.jmat.dashboard.ui.fragment.DashboardInstalledFragment
import com.jmat.dashboard.ui.fragment.DashboardModuleListingsNewFragment
import com.jmat.dashboard.ui.fragment.DashboardModuleListingsPopularFragment

class DashboardModuleListingsViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return DashboardModuleListingsPopularFragment()
            1 -> return DashboardModuleListingsNewFragment()
        }
        throw RuntimeException("Invalid Tab")
    }
}
