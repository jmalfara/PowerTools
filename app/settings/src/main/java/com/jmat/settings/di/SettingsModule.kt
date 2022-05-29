package com.jmat.settings.di

import android.content.Context
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.migration.DisableInstallInCheck

@DisableInstallInCheck
@Module
class SettingsModule {

    @Provides
    fun providesSplitInstallManager(
        @ApplicationContext context: Context
    ): SplitInstallManager {
        return SplitInstallManagerFactory.create(context)
    }
}