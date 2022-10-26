package com.jmat.powertools.data.preferences

import androidx.datastore.core.DataStore
import com.jmat.powertools.Shortcut
import com.jmat.powertools.UserPreferences
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<UserPreferences>
) {
    val data = dataStore.data
    val shortcuts = dataStore.data.map { it.shortcutsList }

    suspend fun addShortcut(
        moduleName: String,
        featureId: String
    ) {
        dataStore.updateData { preferences ->
            val shortcut = Shortcut.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setModuleName(moduleName)
                .setFeatureId(featureId)
                .build()
            preferences.toBuilder().addShortcuts(shortcut).build()
        }
    }

    suspend fun removeShortcut(id: String) {
        dataStore.updateData { preferences ->
            val shortcut = preferences.shortcutsList.find {
                it.featureId == id
            }
            val index = preferences.shortcutsList.indexOf(shortcut)
            preferences.toBuilder().removeShortcuts(index).build()
        }
    }

    suspend fun updateShortcuts(ids: List<String>) {
        dataStore.updateData { preferences ->
            val shortcuts: List<Shortcut> = ids.mapNotNull { shortcutId ->
                preferences.shortcutsList.find { it.id == shortcutId }
            }

            preferences.toBuilder()
                .clearShortcuts()
                .addAllShortcuts(shortcuts)
                .build()
        }
    }

    suspend fun clear() {
        dataStore.updateData { preferences ->
            preferences.defaultInstanceForType
        }
    }
}
