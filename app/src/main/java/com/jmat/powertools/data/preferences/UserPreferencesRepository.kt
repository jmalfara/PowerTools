package com.jmat.powertools.data.preferences

import androidx.datastore.core.DataStore
import com.jmat.powertools.Feature
import com.jmat.powertools.InstalledModule
import com.jmat.powertools.Module
import com.jmat.powertools.Shortcut
import com.jmat.powertools.UserPreferences
import java.util.UUID
import javax.inject.Inject
import com.jmat.powertools.data.model.Module as UiModule

class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<UserPreferences>
) {
    val data = dataStore.data

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

    suspend fun resetModules(
        uiModules: List<UiModule>
    ) {
        val modules = uiModules.map { module ->
            Module.newBuilder()
                .setName(module.name)
                .setAuthor(module.author)
                .setIconUrl(module.iconUrl)
                .setShortDescription(module.shortDescription)
                .setInstallName(module.installName)
                .setEntrypoint(module.entrypoint)
                .addAllFeatures(
                    module.features.map {
                        Feature.newBuilder()
                            .setId(it.id)
                            .setTitle(it.title)
                            .setDescription(it.description)
                            .setModule(it.module)
                            .setIconUrl(it.iconUrl)
                            .setEntrypoint(it.entrypoint)
                            .build()
                    }
                ).build()
        }

        dataStore.updateData { preferences ->
            preferences.toBuilder()
                .clearModules()
                .addAllModules(modules)
                .build()
        }
    }

    suspend fun addInstalledModule(
        moduleName: String
    ) {
        dataStore.updateData { preferences ->
            val installedModule = InstalledModule.newBuilder()
                .setModuleName(moduleName)
                .build()

            preferences.toBuilder()
                .addInstalledModules(installedModule)
                .build()
        }
    }

    suspend fun removeInstalledModule(moduleName: String) {
        dataStore.updateData { preferences ->
            val module = preferences.installedModulesList.find {
                it.moduleName == moduleName
            }
            val index = preferences.installedModulesList.indexOf(module)
            preferences.toBuilder().removeInstalledModules(index).build()
        }
    }

    suspend fun clear() {
        dataStore.updateData { preferences ->
            preferences.defaultInstanceForType
        }
    }
}
