package com.jmat.dashboard.di

import com.jmat.dashboard.ui.fragment.DashboardFavouritesFragment
import com.jmat.powertools.modules.dashboard.DashboardModuleDependencies
import dagger.hilt.android.EntryPointAccessors

fun DashboardFavouritesFragment.inject() {
    DaggerDashboardComponent.builder()
        .appDependencies(
            EntryPointAccessors.fromApplication(
                requireContext(),
                DashboardModuleDependencies::class.java
            )
        ).build().inject(this)
}