package com.jmat.powertools.base.service.factory

import androidx.annotation.VisibleForTesting
import com.jmat.powertools.base.service.client.OkHttpProvider
import com.jmat.powertools.base.service.retrofit.RetrofitProvider
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ServiceClientFactory {

    inline fun <reified T> create(
        clientProvider: OkHttpProvider,
        baseUrl: String,
        networkInterceptors: List<Interceptor> = listOf(),
        applicationInterceptors: List<Interceptor> = listOf()
    ): T = create(
        clazz = T::class.java,
        clientProvider = clientProvider,
        baseUrl = baseUrl,
        networkInterceptors = networkInterceptors,
        applicationInterceptors = applicationInterceptors
    )

    fun <T> create(
        clazz: Class<T>,
        clientProvider: OkHttpProvider,
        baseUrl: String,
        networkInterceptors: List<Interceptor> = listOf(),
        applicationInterceptors: List<Interceptor> = listOf()
    ) : T {
        val okHttp = clientProvider.okHttpClient.newBuilder()
            .apply {
                networkInterceptors().addAll(networkInterceptors)
                interceptors().addAll(applicationInterceptors)
            }.build()

        return createRetrofitBuilder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttp)
            .build()
            .create(clazz)
    }
}

@VisibleForTesting
fun createRetrofitBuilder(): Retrofit.Builder {
    return Retrofit.Builder()
}