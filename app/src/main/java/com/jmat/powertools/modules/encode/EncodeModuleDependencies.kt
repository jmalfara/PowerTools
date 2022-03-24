package com.jmat.powertools.modules.encode

import androidx.datastore.core.DataStore
import com.jmat.powertools.UserPreferences
import com.jmat.powertools.data.memorycache.MemoryCache
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@EntryPoint
@InstallIn(SingletonComponent::class)
interface EncodeModuleDependencies {
    fun providesUserPreferenceDataStore(): DataStore<UserPreferences>
    fun providesMemoryCache(): MemoryCache
    @Named("debug")
    fun provideDebug(): Boolean
}
