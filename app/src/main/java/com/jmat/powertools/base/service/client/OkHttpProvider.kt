package com.jmat.powertools.base.service.client

import okhttp3.OkHttpClient

interface OkHttpProvider {
    val okHttpClient: OkHttpClient
}
