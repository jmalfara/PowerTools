package com.jmat.powertools.modules.settings

import androidx.datastore.core.DataStore
import com.jmat.powertools.UserPreferences
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SettingsModuleDependencies {
    fun providesUserPreferenceDataStore(): DataStore<UserPreferences>
}