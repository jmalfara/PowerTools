package com.jmat.powertools.data.memorycache

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import java.util.*
import javax.inject.Singleton

typealias CacheMap = Map<String, Any>

@Singleton
class MemoryCache {
    private val cacheFlow: MutableSharedFlow<CacheMap> = MutableStateFlow(mapOf())
    val data: Flow<CacheMap> = cacheFlow

    // Need to be careful to not mix keys and types.
    suspend fun put(key: String, data: Any) {
        val newMap = HashMap(cacheFlow.first()).apply {
            put(key, data)
        }
        cacheFlow.emit(newMap)
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun <T> get(key: String): T? {
        return cacheFlow.first()[key] as? T
    }

    suspend fun <T> remove(key: String) {
        val newMap = HashMap(cacheFlow.first()).apply {
            remove(key)
        }
        cacheFlow.emit(newMap)
    }
}