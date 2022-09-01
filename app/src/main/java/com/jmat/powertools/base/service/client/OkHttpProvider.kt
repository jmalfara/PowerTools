package com.jmat.powertools.base.service.client

import io.ktor.client.HttpClient
import okhttp3.OkHttpClient

interface OkHttpProvider {
    val okHttpClient: OkHttpClient
    val ktorClient: HttpClient
}
