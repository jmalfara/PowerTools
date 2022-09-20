package com.jmat.system.di

import com.jmat.powertools.modules.system.SystemModuleDependencies
import com.jmat.system.ui.SystemActivity
import dagger.Component

@Component(
    dependencies = [SystemModuleDependencies::class],
    modules = [SystemModule::class]
)
interface SystemComponent {
    fun inject(fragment: SystemActivity)

    @Component.Builder
    interface Builder {
        fun appDependencies(moduleDependencies: SystemModuleDependencies): Builder
        fun build(): SystemComponent
    }
}