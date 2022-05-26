package com.jmat.powertools.base.data

import android.content.res.Resources
import androidx.annotation.RawRes
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class ResourceService(
    private val resources: Resources,
    private val dispatcher: CoroutineDispatcher,
    private val moshi: Moshi
) {
    private suspend fun loadResourceInternal(@RawRes resourceId: Int) : Result<String> {
        return withContext(dispatcher) {
            try {
                success(
                    resources.openRawResource(resourceId)
                        .bufferedReader()
                        .use { it.readText() }
                )
            } catch (e: Exception) {
                failure(e)
            }
        }
    }

    suspend fun <T>loadResource(@RawRes resourceId: Int, clazz: Class<T>) : Result<T> {
        val result = loadResourceInternal(resourceId)
        return if (result.isSuccess) {
            val obj = moshi.adapter(clazz).fromJson(result.getOrThrow())
            if (obj != null) {
                success(obj)
            } else failure(RuntimeException("Expecting object to be non null"))
        } else failure(result.exceptionOrNull() ?: Exception("Unknown Error"))
    }

    suspend inline fun <reified T>loadResource(@RawRes resourceId: Int) : Result<T> {
        return loadResource(resourceId, T::class.java)
    }
}