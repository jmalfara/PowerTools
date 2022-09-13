package com.jmat.dashboard.di

import com.jmat.dashboard.ui.DashboardActivity
import com.jmat.powertools.modules.dashboard.DashboardModuleDependencies
import dagger.Component

@Component(
    dependencies = [DashboardModuleDependencies::class],
    modules = [DashboardModule::class, DashboardViewModelsModule::class],
)
interface DashboardComponent {
    fun inject(activity: DashboardActivity)

    @Component.Builder
    interface Builder {
        fun appDependencies(dashboardModuleDependencies: DashboardModuleDependencies): Builder
        fun build(): DashboardComponent
    }
}