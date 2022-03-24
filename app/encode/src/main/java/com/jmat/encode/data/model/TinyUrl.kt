package com.jmat.encode.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TinyUrlCreateRequest(
    val url: String,
    val domain: String
)

@JsonClass(generateAdapter = true)
data class TinyUrlCreateResponse(
    val data: TinyUrlCreateData
)

@JsonClass(generateAdapter = true)
data class TinyUrlCreateData(
    val tiny_url: String,
    val url: String,
    val created_at: String,
)

