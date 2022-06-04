package com.jmat.powertools.data.preferences

import androidx.datastore.core.DataStore
import com.jmat.powertools.Favourite
import com.jmat.powertools.Module
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

    suspend fun addModule(
        id: String,
        name: String,
        author: String,
        iconUrl: String,
        shortDescription: String,
        previewUrls: String,
        previewType: String,
        installName: String
    ) {
        dataStore.updateData { preferences ->
            val module = Module.newBuilder()
                .setId(id)
                .setName(name)
                .setAuthor(author)
                .setIconUrl(iconUrl)
                .setShortDescription(shortDescription)
                .setPreviewUrls(previewUrls)
                .setPreviewType(previewType)
                .setInstallName(installName)
                .build()
            preferences.toBuilder().addModules(module).build()
        }
    }

    suspend fun removeModule(id: String) {
        dataStore.updateData { preferences ->
            val module = preferences.modulesList.find {
                it.id == id
            }
            val index = preferences.modulesList.indexOf(module)
            preferences.toBuilder().removeModules(index).build()
        }
    }


//    string id = 1;
//    string name = 2;
//    string author = 3;
//    string iconUrl = 4;
//    string shortDescription = 5;
//    string previewUrls = 6;
//    string previewType = 7;
//    string installName = 8;

    suspend fun clear() {
        dataStore.updateData { preferences ->
            preferences.defaultInstanceForType
        }
    }
}
