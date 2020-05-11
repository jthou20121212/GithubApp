package com.jthou.github.network

import com.jthou.common.ensureDir
import com.jthou.github.AppContext
import com.jthou.github.network.compat.enableTls12OnPreLollipop
import com.jthou.github.network.interceptors.AcceptInterceptor
import com.jthou.github.network.interceptors.AuthInterceptor
import com.jthou.github.network.interceptors.CacheInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory2
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File
import java.util.concurrent.TimeUnit

/**
 *
 *
 * @author jthou
 * @version 1.0.0
 * @date 01-03-2020
 */
private const val BASE_URL = "https://api.github.com"

//通过一个 QueryParameter 让 CacheInterceptor 添加 no-cache
const val FORCE_NETWORK = "forceNetwork"

private val cacheFile by lazy {
    File(AppContext.cacheDir, "webServiceApi").apply { ensureDir() }
}

val retrofit: Retrofit by lazy {
    Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory2.createWithScheduler(Schedulers.io(), AndroidSchedulers.mainThread()))
        .client(
            OkHttpClient
                .Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .cache(Cache(cacheFile, 1024 * 1024 * 1024))
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                })
                .addInterceptor(AuthInterceptor())
                .addInterceptor(AcceptInterceptor())
                .addInterceptor(CacheInterceptor())
                .enableTls12OnPreLollipop()
                .build())
        .baseUrl(BASE_URL)
        .build()
}