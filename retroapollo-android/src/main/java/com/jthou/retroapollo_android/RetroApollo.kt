package com.jthou.retroapollo_android

import com.apollographql.apollo.ApolloClient
import com.jthou.retroapollo_android.utils.Utils
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.lang.reflect.Type
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

class RetroApollo private constructor(
    val apolloClient: ApolloClient,
    val callAdapterFactories: List<CallAdapter.Factory>
) {

    class Builder {

        private var apolloClient: ApolloClient? = null

        fun apolloClient(apolloClient: ApolloClient): Builder {
            this.apolloClient = apolloClient
            return this
        }

        private val callAdapterFactories =
            arrayListOf<CallAdapter.Factory>(ApolloCallAdapterFactory())

        fun addCallAdapterFactory(callAdapterFactory: CallAdapter.Factory): Builder {
            this.callAdapterFactories.add(callAdapterFactory)
            return this
        }

        fun build() = apolloClient?.let {
            RetroApollo(it, callAdapterFactories)
        } ?: throw IllegalStateException("ApolloClient cannot be null.")

    }

    private val serviceMethodCache = ConcurrentHashMap<Method, ApolloServiceMethod<*>>()

    fun <T : Any> createGraphQLService(service: KClass<T>): T {
        Utils.validateServiceInterface(service.java)
        return Proxy.newProxyInstance(
            service.java.classLoader,
            arrayOf(service.java),
            object : InvocationHandler {

                override fun invoke(proxy: Any, method: Method, args: Array<Any>?): Any {
                    if (method.declaringClass == Any::class.java) {
                        return method.invoke(proxy, args)
                    }

                    return loadServiceMethod(method)(args)
                }

            }) as T
    }

    fun loadServiceMethod(method: Method): ApolloServiceMethod<*> {
        var serviceMethod = serviceMethodCache[method]
        if (serviceMethod == null) {
            synchronized(serviceMethodCache) {
                serviceMethod = serviceMethodCache[method] ?: ApolloServiceMethod.Builder(this, method).build().also {
                    serviceMethodCache[method] = it
                }
            }
        }
        return serviceMethod!!
    }

    fun getCallAdapter(type: Type): CallAdapter<Any, Any>? {
        for (callAdapterFactory in callAdapterFactories) {
            val callAdapter = callAdapterFactory.get(type)
            return callAdapter as? CallAdapter<Any, Any>? ?: continue
        }
        return null
    }

}