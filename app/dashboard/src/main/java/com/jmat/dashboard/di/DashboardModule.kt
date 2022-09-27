package com.jmat.dashboard.di

import android.content.Context
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.jmat.dashboard.data.ModuleRepository
import com.jmat.powertools.base.data.ImageDownloadService
import com.jmat.powertools.base.data.ResourceService
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.migration.DisableInstallInCheck
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope

@DisableInstallInCheck
@Module
object DashboardModule {
    private var moduleRepository: ModuleRepository? = null

    @Provides
    fun providesSplitInstallManager(
        @ApplicationContext context: Context
    ): SplitInstallManager {
        return SplitInstallManagerFactory.create(context)
    }

    @OptIn(DelicateCoroutinesApi::class)
    @Provides
    fun provideModuleRepository(
        resourceService: ResourceService,
        imageDownloadService: ImageDownloadService,
        splitInstallManager: SplitInstallManager
    ): ModuleRepository {
        return moduleRepository ?: ModuleRepository(
            resourceService = resourceService,
            imageDownloadService = imageDownloadService,
            splitInstallManager = splitInstallManager,
            coroutineScope = GlobalScope //GlobalScope because this is a singleton
        ).also { moduleRepository = it }
    }
}