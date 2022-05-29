package com.jmat.settings.di

import com.jmat.powertools.modules.settings.SettingsModuleDependencies
import com.jmat.settings.ui.fragment.SettingsDebugFragment
import com.jmat.settings.ui.fragment.SettingsLandingFragment
import dagger.Component

@Component(
    dependencies = [SettingsModuleDependencies::class],
    modules = [SettingsModule::class, SettingsViewModelsModule::class]
)
interface SettingsComponent {
    fun inject(fragment: SettingsLandingFragment)
    fun inject(fragment: SettingsDebugFragment)

    @Component.Builder
    interface Builder {
        fun appDependencies(dependencies: SettingsModuleDependencies): Builder
        fun build(): SettingsComponent
    }
}