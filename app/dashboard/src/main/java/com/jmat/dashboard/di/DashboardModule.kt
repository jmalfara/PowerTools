package com.jmat.dashboard.di

import android.content.Context
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
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