package com.jmat.encode.data

import com.jmat.encode.data.model.TinyUrlCreateRequest
import com.jmat.encode.data.model.TinyUrlCreateResponse
import com.jmat.encode.data.service.TinyUrlService
import com.jmat.powertools.base.service.ApiResult
import com.jmat.powertools.base.service.ApiSuccess
import com.jmat.powertools.base.service.safeApiCall
import com.jmat.powertools.data.memorycache.MemoryCache
import javax.inject.Inject

class EncodeRepository @Inject constructor (
    val tinyUrlService: TinyUrlService,
    val memoryCache: MemoryCache
) {
    private val apiKey = "VB2WNMfCApvfVywGhw403yQIL9VbtUtJp6qtKUMeRsv5DWpqUpQMlL0iY41h"

    suspend fun createTinyUrl(request: TinyUrlCreateRequest) : ApiResult<Unit> {
        return safeApiCall {
            tinyUrlService.createTinyUrl(
                "Bearer $apiKey",
                request
            )
            Unit
        }
    }
}