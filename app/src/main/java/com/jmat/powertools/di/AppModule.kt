package com.jmat.powertools.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.bumptech.glide.Glide
import com.jmat.powertools.BuildConfig
import com.jmat.powertools.UserPreferences
import com.jmat.powertools.base.data.ImageDownloadService
import com.jmat.powertools.base.data.ResourceService
import com.jmat.powertools.data.memorycache.MemoryCache
import com.jmat.powertools.data.preferences.UserPreferencesSerializer
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesUserPreferencesDataStore(
        @ApplicationContext context: Context
    ): DataStore<UserPreferences> {
        return DataStoreFactory.create(
            serializer = UserPreferencesSerializer,
            produceFile = { context.dataStoreFile("user_prefs.pb") }
        )
    }

    @Provides
    @Named("debug")
    fun provideDebug(): Boolean {
        return BuildConfig.DEBUG
    }

    @Provides
    @Singleton
    fun provideMemoryCache(): MemoryCache {
        return MemoryCache()
    }

    @Provides
    fun provideImageDownloadService(
        @ApplicationContext context: Context
    ) : ImageDownloadService {
        return ImageDownloadService(
            requestManager = Glide.with(context)
        )
    }

    @Provides
    fun provideResourceService(
        @ApplicationContext context: Context,
        moshi: Moshi
    ): ResourceService {
        return ResourceService(
            resources = context.resources,
            dispatcher = Dispatchers.IO,
            moshi = moshi
        )
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }
}
