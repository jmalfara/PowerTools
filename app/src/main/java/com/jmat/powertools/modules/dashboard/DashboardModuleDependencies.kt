package com.jmat.powertools.modules.dashboard

import android.content.Context
import androidx.datastore.core.DataStore
import com.jmat.powertools.UserPreferences
import com.jmat.powertools.base.data.ImageDownloadService
import com.jmat.powertools.base.data.ResourceService
import com.squareup.moshi.Moshi
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DashboardModuleDependencies {
    fun providesUserPreferenceDataStore(): DataStore<UserPreferences>
    @ApplicationContext
    fun applicationContext(): Context
    fun provideImageDownloadService(): ImageDownloadService
    fun provideResourceService(): ResourceService
    fun provideMoshi(): Moshi
}
