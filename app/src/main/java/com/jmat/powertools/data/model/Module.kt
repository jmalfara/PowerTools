package com.jmat.powertools.data.model

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Module(
    val name: String,
    val author: String,
    val iconUrl: String,
    val shortDescription: String,
    val installName: String,
    val entrypoint: String,
    val features: List<Feature>
) : Serializable

@JsonClass(generateAdapter = true)
data class Feature(
    val id: String,
    val title: String,
    val description: String,
    val module: String,
    val iconUrl: String,
    val entrypoint: String
) : Serializable