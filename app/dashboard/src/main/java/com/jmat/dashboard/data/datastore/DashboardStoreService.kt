package com.jmat.dashboard.data.datastore

import androidx.datastore.core.DataStore
import com.jmat.powertools.Feature
import com.jmat.powertools.InstalledModule
import com.jmat.powertools.Module
import com.jmat.powertools.UserPreferences
import javax.inject.Inject

class DashboardStoreService @Inject constructor(
    private val dataStore: DataStore<UserPreferences>
) {
    val data = dataStore.data

    suspend fun resetModules(
        uiModules: List<com.jmat.dashboard.data.model.Module>
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
