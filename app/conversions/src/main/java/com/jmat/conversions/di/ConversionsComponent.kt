package com.jmat.conversions.di

import com.jmat.conversions.ui.fragment.ConversionKilometersToMilesFragment
import com.jmat.conversions.ui.fragment.ConversionLiters100KmToMPGFragment
import com.jmat.conversions.ui.fragment.ConversionMilliliterToOunceFragment
import com.jmat.powertools.modules.conversions.ConversionsModuleDependencies
import dagger.Component

@Component(
    dependencies = [ConversionsModuleDependencies::class],
    modules = [ConversionsModule::class]
)
interface ConversionsComponent {
    fun inject(fragment: ConversionMilliliterToOunceFragment)
    fun inject(fragment: ConversionKilometersToMilesFragment)
    fun inject(fragment: ConversionLiters100KmToMPGFragment)

    @Component.Builder
    interface Builder {
        fun appDependencies(moduleDependencies: ConversionsModuleDependencies): Builder
        fun build(): ConversionsComponent
    }
}