package com.jmat.dashboard.data.model

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class ModuleListings(
    val popularModules: List<Module>,
    val newModules: List<Module>
) : Serializable

@JsonClass(generateAdapter = true)
data class Module(
    val id: String,
    val name: String,
    val author: String,
    val iconUrl: String,
    val shortDescription: String,
    val previewUrls: List<String>,
    val previewType: String
) : Serializable