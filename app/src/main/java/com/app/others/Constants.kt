package com.app.others

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object Constants {

    object Services {
        val BASE_URL = "https://newsapi.org/"
    }

    fun setTimeOut(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient().newBuilder()
            .connectTimeout(100 * 10, TimeUnit.SECONDS)
            .readTimeout(100 * 10, TimeUnit.SECONDS)
            .writeTimeout(100 * 10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor { chain ->
                val request = chain.request()
                    ?.newBuilder()
                    ?.addHeader("Content-Type", "application/json")
                    ?.build()
                chain.proceed(request)
            }
            .addInterceptor(loggingInterceptor)
            .build()
    }

    object Keys {
        val _jsonString = "jsonString"
        val _isOffline = ""
        val _apiKeys = "4eb1483620174fba970d74a1cd7e300f"
        val _publishedAt = "publishedAt"
        val _date = "2019-08-24"

    }
}