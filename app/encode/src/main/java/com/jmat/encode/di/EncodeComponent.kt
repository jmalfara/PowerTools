package com.jmat.encode.di

import com.jmat.encode.EncodeActivity
import com.jmat.encode.ui.fragment.EncodeGetDataFragment
import com.jmat.encode.ui.fragment.EncodeTinyUrlCreateFragment
import com.jmat.encode.ui.fragment.EncodeTinyUrlFragment
import com.jmat.powertools.modules.encode.EncodeModuleDependencies
import dagger.Component

@Component(
    dependencies = [EncodeModuleDependencies::class],
    modules = [EncodeModule::class, EncodeViewModelsModule::class]
)
interface EncodeComponent {
    fun inject(fragment: EncodeTinyUrlFragment)
    fun inject(fragment: EncodeTinyUrlCreateFragment)
    fun inject(fragment: EncodeGetDataFragment)
    fun inject(activity: EncodeActivity)

    @Component.Builder
    interface Builder {
        fun appDependencies(dependencies: EncodeModuleDependencies): Builder
        fun build(): EncodeComponent
    }
}