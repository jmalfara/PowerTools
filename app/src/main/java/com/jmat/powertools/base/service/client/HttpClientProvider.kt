package com.jmat.powertools.base.service.client

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class HttpClientProvider(
    debug: Boolean
) : OkHttpProvider {
    override val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                setLevel(
                    if (debug) {
                        HttpLoggingInterceptor.Level.BODY
                    } else HttpLoggingInterceptor.Level.NONE
                )
            }
        )
        .build()
}