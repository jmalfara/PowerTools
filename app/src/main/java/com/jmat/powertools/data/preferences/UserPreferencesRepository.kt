package com.jmat.powertools.data.preferences

import androidx.datastore.core.DataStore
import com.jmat.powertools.Favourite
import com.jmat.powertools.TinyUrl
import com.jmat.powertools.UserPreferences
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor (
    private val dataStore: DataStore<UserPreferences>
) {
    val data = dataStore.data

    suspend fun addFavorite(id: String, deeplink: String) {
        dataStore.updateData { preferences ->
            val favourite = Favourite.newBuilder()
                .setId(id)
                .setDeeplink(deeplink)
                .build()
            preferences.toBuilder().addFavourites(favourite).build()
        }
    }

    suspend fun removeFavourite(id: String) {
        dataStore.updateData { preferences ->
            val favourite = preferences.favouritesList.find {
                it.id == id
            }
            val index = preferences.favouritesList.indexOf(favourite)
            preferences.toBuilder().removeFavourites(index).build()
        }
    }

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

    suspend fun clear() {
        dataStore.updateData { preferences ->
            preferences.defaultInstanceForType
        }
    }
}