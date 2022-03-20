package com.jmat.encode.di

import com.jmat.encode.ui.EncodeTinyUrlFragment
import com.jmat.powertools.modules.encode.EncodeModuleDependencies
import dagger.Component

@Component(
    dependencies = [EncodeModuleDependencies::class],
    modules = [EncodeModule::class, EncodeViewModelsModule::class]
)
interface EncodeComponent {
    fun inject(fragment: EncodeTinyUrlFragment)

    @Component.Builder
    interface Builder {
        fun appDependencies(dependencies: EncodeModuleDependencies): Builder
        fun build(): EncodeComponent
    }
}