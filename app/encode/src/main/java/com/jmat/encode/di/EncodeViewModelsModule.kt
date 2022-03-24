package com.jmat.encode.di

import androidx.lifecycle.ViewModel
import com.jmat.encode.ui.viewmodel.EncodeTinyUrlCreateViewModel
import com.jmat.encode.ui.viewmodel.EncodeTinyUrlViewModel
import com.jmat.powertools.base.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import dagger.multibindings.IntoMap

@DisableInstallInCheck
@Module
abstract class EncodeViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(EncodeTinyUrlCreateViewModel::class)
    abstract fun bindEncodeTinyUrlCreateViewModel(viewModel: EncodeTinyUrlCreateViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EncodeTinyUrlViewModel::class)
    abstract fun bindEncodeTinyUrlViewModel(viewModel: EncodeTinyUrlViewModel): ViewModel
}