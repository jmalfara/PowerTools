package com.jmat.dashboard.data.model

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class ModuleListings(
    val popular: List<Listing>,
    val new: List<Listing>
) : Serializable

@JsonClass(generateAdapter = true)
data class Listing(
    val installName: String,
    val previewUrls: List<String>,
    val previewType: String
) : Serializable

@JsonClass(generateAdapter = true)
data class Modules(
    val modules: List<Module>
) : Serializable

@JsonClass(generateAdapter = true)
data class Module(
    val name: String,
    val author: String,
    val iconUrl: String,
    val shortDescription: String,
    val installName: String,
    val entrypoint: String
) : Serializable

@JsonClass(generateAdapter = true)
data class Feature(
    val id: String,
    val title: String,
    val iconUrl: String,
    val entrypoint: String
)
