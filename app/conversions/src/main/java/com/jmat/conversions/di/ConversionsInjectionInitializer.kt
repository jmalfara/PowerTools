package com.jmat.conversions.di

import androidx.fragment.app.Fragment
import com.jmat.powertools.modules.conversions.ConversionsModuleDependencies
import dagger.hilt.android.EntryPointAccessors

interface InjectionInitializer<T> {
    fun Fragment.buildComponent(): T
}

class ConversionsInjectionInitializer : InjectionInitializer<ConversionsComponent> {
    override fun Fragment.buildComponent(): ConversionsComponent {
        return DaggerConversionsComponent.builder().appDependencies(
            EntryPointAccessors.fromApplication(
                requireContext(),
                ConversionsModuleDependencies::class.java
            )
        ).build()
    }
}