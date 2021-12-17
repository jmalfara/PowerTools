package com.jmat.powertools.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.jmat.powertools.Favourite
import com.jmat.powertools.UserPreferences

private const val DATA_STORE_FILE_NAME = "user_prefs.pb"

val Context.userPreferencesStore: DataStore<UserPreferences> by dataStore(
    fileName = DATA_STORE_FILE_NAME,
    serializer = UserPreferencesSerializer
)

class UserPreferencesRepository(
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
}