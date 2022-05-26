package com.jmat.dashboard.di

import com.jmat.dashboard.ui.fragment.DashboardFavouritesFragment
import com.jmat.dashboard.ui.fragment.DashboardStoreFragment
import com.jmat.powertools.modules.dashboard.DashboardModuleDependencies
import dagger.Component

@Component(
    dependencies = [DashboardModuleDependencies::class],
    modules = [DashboardModule::class, DashboardViewModelsModule::class]
)
interface DashboardComponent {
    fun inject(fragment: DashboardFavouritesFragment)
    fun inject(fragment: DashboardStoreFragment)

    @Component.Builder
    interface Builder {
        fun appDependencies(dashboardModuleDependencies: DashboardModuleDependencies): Builder
        fun build(): DashboardComponent
    }
}