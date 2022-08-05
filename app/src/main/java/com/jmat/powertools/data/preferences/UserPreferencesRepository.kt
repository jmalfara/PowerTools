package com.jmat.powertools.data.preferences

import androidx.datastore.core.DataStore
import com.jmat.powertools.Favourite
import com.jmat.powertools.Feature
import com.jmat.powertools.InstalledModule
import com.jmat.powertools.Module
import com.jmat.powertools.data.model.Module as UiModule
import com.jmat.powertools.UserPreferences
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<UserPreferences>
) {
    val data = dataStore.data

    suspend fun addFavorite(
        moduleName: String,
        featureId: String
    ) {
        dataStore.updateData { preferences ->
            val favourite = Favourite.newBuilder()
                .setModuleName(moduleName)
                .setFeatureId(featureId)
                .build()
            preferences.toBuilder().addFavourites(favourite).build()
        }
    }

    suspend fun removeFavourite(id: String) {
        dataStore.updateData { preferences ->
            val favourite = preferences.favouritesList.find {
                it.featureId == id
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
//        dataStore.updateData { preferences ->
//            val tinyUrl = TinyUrl.newBuilder()
//                .setId(id)
//                .setUrl(url)
//                .setOriginalUrl(originalUrl)
//                .setCreatedAt(createdAt)
//                .build()
//            preferences.toBuilder().addTinyUrls(tinyUrl).build()
//        }
    }

//    suspend fun deleteTinyUrls(
//        urls: List<TinyUrl>
//    ) {
////        dataStore.updateData { preferences ->
////            urls.foldRight(preferences.toBuilder()) { tinyUrl, builder ->
////                val index = preferences.tinyUrlsList.indexOf(tinyUrl)
////                builder.removeTinyUrls(index)
////            }.build()
////        }
//    }

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
                .addAllFeatures(module.features.map {
                    Feature.newBuilder()
                        .setId(it.id)
                        .setTitle(it.title)
                        .setDescription(it.description)
                        .setModule(it.module)
                        .setIconUrl(it.iconUrl)
                        .setEntrypoint(it.entrypoint)
                        .build()
                }).build()
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
