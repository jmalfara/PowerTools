package com.jmat.dashboard.data.model

import com.jmat.powertools.data.model.Module
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

