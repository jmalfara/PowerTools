package com.jmat.dashboard.di

import com.jmat.dashboard.ui.DashboardActivity
import com.jmat.dashboard.ui.fragment.*
import com.jmat.powertools.modules.dashboard.DashboardModuleDependencies
import dagger.Component

@Component(
    dependencies = [DashboardModuleDependencies::class],
    modules = [DashboardModule::class, DashboardViewModelsModule::class],
)
interface DashboardComponent {
    fun inject(activity: DashboardActivity)
    fun inject(fragment: DashboardFavouritesFragment)
    fun inject(fragment: DashboardInstalledFragment)
    fun inject(fragment: DashboardModuleListingsFragment)
    fun inject(fragment: DashboardModuleListingsPopularFragment)
    fun inject(fragment: DashboardModuleListingsNewFragment)
    fun inject(fragment: DashboardModuleDetailsFragment)

    @Component.Builder
    interface Builder {
        fun appDependencies(dashboardModuleDependencies: DashboardModuleDependencies): Builder
        fun build(): DashboardComponent
    }
}