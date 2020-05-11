package com.jthou.github.network.interceptors

import com.jthou.common.logger
import com.jthou.common.otherwise
import com.jthou.common.yes
import com.jthou.github.network.FORCE_NETWORK
import com.jthou.github.network.Network
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class CacheInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        var request = Network
            .isAvailable()
            .yes {
                original.url.queryParameter(FORCE_NETWORK)?.toBoolean()?.let {
                    it.yes {
                        // 注意 noCache | noStore，前者不会读缓存；后者既不读缓存，也不对响应进行缓存
                        // 尽管看上去 noCache 比较符合我们的需求，但服务端仍然可能返回服务端的缓存
                        // request.newBuilder().cacheControl(CacheControl.Builder().noCache().build()).build()
                        original.newBuilder().cacheControl(
                            CacheControl.Builder().maxAge(0, TimeUnit.SECONDS).build()
                        ).build()
                    }.otherwise {
                        original
                    }
                } ?: original
            }
            .otherwise {
                original.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
            }
        request = request.newBuilder().url(request.url.newBuilder().removeAllQueryParameters(FORCE_NETWORK).build()).build()
        return chain.proceed(request).also { response ->
            logger.error("Cache: ${response.cacheResponse}, Network: ${response.networkResponse}")
        }
    }

}