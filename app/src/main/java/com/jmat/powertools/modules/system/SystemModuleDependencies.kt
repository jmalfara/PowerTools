package com.jmat.powertools.modules.system

import androidx.datastore.core.DataStore
import com.jmat.powertools.UserPreferences
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SystemModuleDependencies {
    fun providesUserPreferenceDataStore(): DataStore<UserPreferences>
}
