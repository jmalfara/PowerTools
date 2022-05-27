package com.jmat.dashboard.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.jmat.powertools.base.data.ImageDownloadService
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.migration.DisableInstallInCheck

@DisableInstallInCheck
@Module
object DashboardModule {
    /* Add Dashboard DI stuff here */

    @Provides
    fun providesSplitInstallManager(
        @ApplicationContext context: Context
    ): SplitInstallManager {
        return SplitInstallManagerFactory.create(context)
    }
}