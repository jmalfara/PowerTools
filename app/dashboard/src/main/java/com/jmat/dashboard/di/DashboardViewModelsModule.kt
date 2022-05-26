package com.jmat.dashboard.di

import androidx.lifecycle.ViewModel
import com.jmat.dashboard.ui.viewmodel.DashboardStoreViewModel
import com.jmat.dashboard.ui.viewmodel.DashboardViewModel
import com.jmat.powertools.base.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import dagger.multibindings.IntoMap

// https://medium.com/@royanimesh2211/injecting-viewmodels-using-dagger-2-with-map-multibinding-on-android-d227a39b017b
@Module
@DisableInstallInCheck
abstract class DashboardViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    abstract fun bindDashboardViewModel(viewModel: DashboardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DashboardStoreViewModel::class)
    abstract fun bindDashboardStoreViewModel(viewModel: DashboardStoreViewModel): ViewModel
}