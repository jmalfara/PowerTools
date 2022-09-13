package com.jmat.dashboard.di

import com.jmat.dashboard.ui.DashboardActivity
import com.jmat.dashboard.ui.fragment.DashboardFragment
import com.jmat.dashboard.ui.fragment.DashboardModuleDetailsFragment
import com.jmat.powertools.modules.dashboard.DashboardModuleDependencies
import dagger.Component

@Component(
    dependencies = [DashboardModuleDependencies::class],
    modules = [DashboardModule::class, DashboardViewModelsModule::class],
)
interface DashboardComponent {
    fun inject(activity: DashboardActivity)
    fun inject(fragment: DashboardModuleDetailsFragment)
    fun inject(fragment: DashboardFragment)

    @Component.Builder
    interface Builder {
        fun appDependencies(dashboardModuleDependencies: DashboardModuleDependencies): Builder
        fun build(): DashboardComponent
    }
}