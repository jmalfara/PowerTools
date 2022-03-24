package com.jmat.powertools.base.service

suspend fun <T> safeApiCall(
    call: suspend () -> T
): ApiResult<T> {
    return try {
        ApiSuccess(call())
    } catch (e: Exception) {
        e.printStackTrace()
        ApiError(e)
    }
}

sealed interface ApiResult<out T>
data class ApiSuccess<T>(val value: T) : ApiResult<T>
data class ApiError(val error: Exception) : ApiResult<Nothing>
