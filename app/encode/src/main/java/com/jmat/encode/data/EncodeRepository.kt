package com.jmat.encode.data

import com.jmat.encode.data.model.TinyUrlCreateRequest
import com.jmat.encode.data.service.TinyUrlService
import com.jmat.powertools.TinyUrl
import com.jmat.powertools.base.service.ApiResult
import com.jmat.powertools.base.service.ApiSuccess
import com.jmat.powertools.base.service.safeApiCall
import com.jmat.powertools.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import java.util.*
import javax.inject.Inject

class EncodeRepository @Inject constructor (
    private val tinyUrlService: TinyUrlService,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    val tinyUrls: Flow<List<TinyUrl>> = userPreferencesRepository.data.transform { data ->
        emit(data.tinyUrlsList)
    }

    private val apiKey = "VB2WNMfCApvfVywGhw403yQIL9VbtUtJp6qtKUMeRsv5DWpqUpQMlL0iY41h"

    suspend fun createTinyUrl(request: TinyUrlCreateRequest) : ApiResult<Unit> {
        return safeApiCall {
            val response = tinyUrlService.createTinyUrl(
                "Bearer $apiKey",
                request
            ).data

            userPreferencesRepository.addTinyUrl(
                id = UUID.randomUUID().toString(),
                createdAt = response.created_at,
                url = response.tiny_url,
                originalUrl = response.url
            )
        }
    }

    suspend fun deleteTinyUrls(urls: List<TinyUrl>) : ApiResult<Unit> {
        userPreferencesRepository.deleteTinyUrls(urls)
        return ApiSuccess(Unit)
    }
}