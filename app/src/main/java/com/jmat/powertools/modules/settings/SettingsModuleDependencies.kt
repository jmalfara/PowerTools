package com.jmat.powertools.modules.settings

import android.content.Context
import androidx.datastore.core.DataStore
import com.jmat.powertools.UserPreferences
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SettingsModuleDependencies {
    @ApplicationContext
    fun applicationContext(): Context
    fun providesUserPreferenceDataStore(): DataStore<UserPreferences>
}
