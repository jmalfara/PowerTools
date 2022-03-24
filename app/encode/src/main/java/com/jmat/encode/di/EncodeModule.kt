package com.jmat.encode.di

import com.jmat.encode.data.service.TinyUrlService
import com.jmat.powertools.base.service.client.HttpClientProvider
import com.jmat.powertools.base.service.factory.ServiceClientFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Named

@Module
@DisableInstallInCheck
class EncodeModule {

    @Provides
    fun provideTinyUrlService(
        @Named("debug") debug: Boolean
    ) : TinyUrlService {
        return ServiceClientFactory.create(
            clientProvider = HttpClientProvider(debug),
            baseUrl = "https://api.tinyurl.com"
        )
    }
}