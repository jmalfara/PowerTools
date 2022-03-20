package com.jmat.powertools.modules.encode

import com.jmat.powertools.data.memorycache.MemoryCache
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface EncodeModuleDependencies {
    fun providesMemoryCache(): MemoryCache
}