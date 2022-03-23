package com.jmat.powertools.modules.encode

import com.jmat.powertools.data.memorycache.MemoryCache
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@EntryPoint
@InstallIn(SingletonComponent::class)
interface EncodeModuleDependencies {
    fun providesMemoryCache(): MemoryCache
    @Named("debug")
    fun provideDebug(): Boolean
}