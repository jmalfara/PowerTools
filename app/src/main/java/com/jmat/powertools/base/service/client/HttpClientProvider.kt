package com.jmat.powertools.base.service.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject
import javax.inject.Named

class HttpClientProvider @Inject constructor(
    @Named("debug") debug: Boolean
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

    override val ktorClient: HttpClient = HttpClient(CIO) {
        install(Logging) {
            logger = Logger.SIMPLE
            level = if (debug) {
                LogLevel.ALL
            } else LogLevel.NONE
        }
//        install(ContentNegotiation) {
//            json(Json {
//                prettyPrint = true
//                isLenient = false
//            })
//        }
    }
}
