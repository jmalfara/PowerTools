package com.jmat.encode.data.service

import com.jmat.powertools.base.service.client.HttpClientProvider
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class SimpleHttpService @Inject constructor(
    private val httpClientProvider: HttpClientProvider
) {
    suspend fun getData(
        url: String
    ): String {
        return httpClientProvider.ktorClient.get(url).body()
    }
}