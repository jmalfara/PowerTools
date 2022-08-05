package com.jmat.encode.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.jmat.encode.data.datastore.EncodeStoreSerializer
import com.jmat.encode.data.service.TinyUrlService
import com.jmat.powertools.EncodeStore
import com.jmat.powertools.base.service.client.HttpClientProvider
import com.jmat.powertools.base.service.factory.ServiceClientFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Named

private lateinit var encodeDataStore: DataStore<EncodeStore>

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

    @Provides
    fun provideEncodeStore(
        @ApplicationContext context: Context
    ) : DataStore<EncodeStore> {
        return if (::encodeDataStore.isInitialized) {
            encodeDataStore
        } else {
            DataStoreFactory.create(
                serializer = EncodeStoreSerializer,
                produceFile = { context.dataStoreFile("encode_store.pb") }
            ).apply {
                encodeDataStore = this
            }
        }
    }
}