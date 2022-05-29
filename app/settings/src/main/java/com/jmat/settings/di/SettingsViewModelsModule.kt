package com.jmat.settings.di

import androidx.lifecycle.ViewModel
import com.jmat.powertools.base.di.ViewModelKey
import com.jmat.settings.ui.viewmodel.SettingsDebugViewModel
import com.jmat.settings.ui.viewmodel.SettingsLandingViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import dagger.multibindings.IntoMap

@DisableInstallInCheck
@Module
abstract class SettingsViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(SettingsLandingViewModel::class)
    abstract fun bindSettingsLandingViewModel(viewModel: SettingsLandingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsDebugViewModel::class)
    abstract fun bindSettingsDebugViewModel(viewModel: SettingsDebugViewModel): ViewModel
}