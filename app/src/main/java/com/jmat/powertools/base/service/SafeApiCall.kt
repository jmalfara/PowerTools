package com.jmat.powertools.base.service

import java.lang.Exception

suspend fun <T> safeApiCall(
    call: suspend () -> T
) : ApiResult<T> {
    return try {
        ApiSuccess(call())
    } catch (e: Exception) {
        ApiError(e)
    }
}

sealed interface ApiResult<out T>
data class ApiSuccess<T>(val value: T) : ApiResult<T>
data class ApiError(val error: Exception) : ApiResult<Nothing>
