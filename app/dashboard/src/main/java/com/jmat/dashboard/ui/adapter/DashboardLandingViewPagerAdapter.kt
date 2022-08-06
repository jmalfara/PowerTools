package com.jmat.dashboard.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jmat.dashboard.ui.fragment.DashboardShortcutsFragment
import com.jmat.dashboard.ui.fragment.DashboardInstalledFragment

class DashboardLandingViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments = listOf(
        DashboardShortcutsFragment::class.java,
        DashboardInstalledFragment::class.java,
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position].newInstance()
    }
}
