package com.jmat.dashboard.di

import com.jmat.dashboard.ui.viewmodel.DashboardViewModel
import com.jmat.powertools.modules.dashboard.DashboardModuleDependencies
import dagger.Component

@Component(
    dependencies = [DashboardModuleDependencies::class],
    modules = [DashboardModule::class, DashboardViewModelsModule::class],
)
@DashboardScope
interface DashboardComponent {
    @Component.Builder
    interface Builder {
        fun appDependencies(dashboardModuleDependencies: DashboardModuleDependencies): Builder
        fun build(): DashboardComponent
    }

    fun getViewModel(): DashboardViewModel
}