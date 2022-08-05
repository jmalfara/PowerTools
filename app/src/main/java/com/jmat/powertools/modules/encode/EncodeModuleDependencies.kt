package com.jmat.powertools.modules.encode

import android.content.Context
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@EntryPoint
@InstallIn(SingletonComponent::class)
interface EncodeModuleDependencies {
    @Named("debug")
    fun provideDebug(): Boolean
    @ApplicationContext
    fun provideApplicationContext(): Context
}
