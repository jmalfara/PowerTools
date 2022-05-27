package com.jmat.dashboard.data

import com.jmat.dashboard.R
import com.jmat.dashboard.data.model.ModuleListings
import com.jmat.powertools.base.data.ImageDownloadService
import com.jmat.powertools.base.data.ResourceService
import javax.inject.Inject

class ModuleRepository @Inject constructor(
    private val resourceService: ResourceService,
    private val imageDownloadService: ImageDownloadService
) {
    suspend fun fetchModules(): Result<ModuleListings> {
        return resourceService.loadResource<ModuleListings>(R.raw.store_listings)
            .onSuccess { moduleListings ->
                moduleListings.newModules
                    .forEach {
                        imageDownloadService.downloadImages(listOf(it.iconUrl))
                        imageDownloadService.downloadImages(it.previewUrls)
                    }
                moduleListings.popularModules
                    .forEach {
                        imageDownloadService.downloadImages(listOf(it.iconUrl))
                        imageDownloadService.downloadImages(it.previewUrls)
                    }
            }
    }
}