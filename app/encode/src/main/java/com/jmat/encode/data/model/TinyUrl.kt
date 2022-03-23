package com.jmat.encode.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TinyUrlCreateRequest(
    val url: String,
    val domain: String,
    val alias: String,
    val tags: String
)

@JsonClass(generateAdapter = true)
data class TinyUrlCreateResponse(
    val data: String,
    val code: Int,
    val errors: String
)