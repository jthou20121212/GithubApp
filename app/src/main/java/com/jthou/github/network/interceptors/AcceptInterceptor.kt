package com.jthou.github.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

/**
 *
 *
 * @author jthou
 * @version 1.0.0
 * @date 01-03-2020
 */

class AcceptInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return chain.proceed(request
            .newBuilder()
            .apply {
                header("accept", "application/vnd.github.v3.full+json, ${request.header("accept") ?: ""}")
            }
            .build())
    }

}