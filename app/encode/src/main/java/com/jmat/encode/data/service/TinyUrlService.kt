package com.jmat.encode.data.service

import com.jmat.encode.data.model.TinyUrlCreateRequest
import com.jmat.encode.data.model.TinyUrlCreateResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TinyUrlService {
    @POST("/create")
    suspend fun createTinyUrl(
        @Header("Authorization") apiKey: String,
        @Body request: TinyUrlCreateRequest
    ) : TinyUrlCreateResponse
}