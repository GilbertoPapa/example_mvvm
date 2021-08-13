package com.gilbertopapa.dev.androidmvvm.di

import android.content.Context
import android.os.Build
import com.ardyyy.dev.androidmvvm.BuildConfig
import com.ardyyy.dev.androidmvvm.data.remote.ApiService
import com.google.android.gms.security.ProviderInstaller
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

fun createWebService(context: Context): ApiService {
    val moshi = Moshi.Builder().build()
    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.SERVER_URL)
        .client(provideOkHttpClient(context))
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
    return retrofit.create(ApiService::class.java)
}

private fun httpInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private fun parameteraInterceptor() = object: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val url: HttpUrl =
            request.url.newBuilder().addQueryParameter("api_key", BuildConfig.API_KEY).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}

fun provideOkHttpClient(context: Context): OkHttpClient {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        ProviderInstaller.installIfNeeded(context)
    }
    val httpClient = OkHttpClient.Builder()
    httpClient.apply {
        writeTimeout(60, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        callTimeout(60, TimeUnit.SECONDS)
        addInterceptor(parameteraInterceptor())
        if (BuildConfig.DEBUG) {
//            addInterceptor(ChuckInterceptor(context))
            addInterceptor(httpInterceptor())
        }
    }
    return httpClient.build()
}