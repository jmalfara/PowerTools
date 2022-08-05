package com.jmat.encode.data.datastore

import androidx.datastore.core.DataStore
import com.jmat.powertools.EncodeStore
import com.jmat.powertools.TinyUrl
import javax.inject.Inject
import javax.inject.Singleton

class EncodeStoreService @Inject constructor(
    private val dataStore: DataStore<EncodeStore>
) {
    val data = dataStore.data

    suspend fun addTinyUrl(
        id: String,
        url: String,
        originalUrl: String,
        createdAt: String
    ) {
        dataStore.updateData { preferences ->
            val tinyUrl = TinyUrl.newBuilder()
                .setId(id)
                .setUrl(url)
                .setOriginalUrl(originalUrl)
                .setCreatedAt(createdAt)
                .build()
            preferences.toBuilder().addTinyUrls(tinyUrl).build()
        }
    }

    suspend fun deleteTinyUrls(
        urls: List<TinyUrl>
    ) {
        dataStore.updateData { preferences ->
            urls.foldRight(preferences.toBuilder()) { tinyUrl, builder ->
                val index = preferences.tinyUrlsList.indexOf(tinyUrl)
                builder.removeTinyUrls(index)
            }.build()
        }
    }
}