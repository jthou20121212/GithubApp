package com.jthou.retroapollo_android.rxjava

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.jthou.retroapollo_android.CallAdapter
import com.jthou.retroapollo_android.utils.Utils
import rx.Observable
import rx.Scheduler
import rx.Single
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

enum class RxReturnType {
    OBSERVABLE, SINGLE
}

class RxJavaCallAdapter<T> constructor(
    val rxReturnType: RxReturnType,
    val dataType: Type,
    val subscribeScheduler: Scheduler? = null,
    val observableScheduler: Scheduler? = null
) : CallAdapter<T, Any> {
    override fun responseType(): Type {
        return if (dataType is ParameterizedType) {
            Utils.getParameterUpperBound(0, dataType)
        } else {
            dataType
        }
    }

    override fun adapt(call: ApolloCall<T>): Any {
        val callFunc = CallExecuteOnSubscribe(call)
        var originalObservable = Observable.create(callFunc)
        originalObservable = subscribeScheduler?.let { originalObservable.subscribeOn(subscribeScheduler) } ?: originalObservable
        originalObservable = observableScheduler?.let { originalObservable.subscribeOn(observableScheduler) } ?: originalObservable

        val observable : Observable<*> =
        // Observable<Response<Data>>
        if (dataType is ParameterizedType) {
            originalObservable
        } else {
            originalObservable.map { it.data() }
        }

        return when(rxReturnType) {
            RxReturnType.OBSERVABLE -> observable
            RxReturnType.SINGLE -> observable.toSingle()
        }
    }

}

class RxJavaCallAdapterFactory : CallAdapter.Factory() {

    private var subscribeScheduler: Scheduler? = null
    private var observableScheduler: Scheduler? = null

    fun subscribeScheduler(subscribeScheduler: Scheduler?) : RxJavaCallAdapterFactory {
        this.subscribeScheduler = subscribeScheduler;
        return this
    }

    fun observableScheduler(observableScheduler: Scheduler?) : RxJavaCallAdapterFactory {
        this.observableScheduler = observableScheduler;
        return this
    }

    override fun get(returnType: Type): CallAdapter<*, *>? {
        val rawType = getRawType(returnType)
        val dataType = getParameterUpperBound(0, returnType as ParameterizedType)
        if (dataType is ParameterizedType) {
            if (getRawType(dataType) != Response::class.java) {
                return null
            }
        }

        val rxReturnType = when(rawType) {
            Observable::class.java -> RxReturnType.OBSERVABLE
            Single::class.java -> RxReturnType.SINGLE
            else -> null
        } ?: return null

        return RxJavaCallAdapter<Any>(rxReturnType, dataType, subscribeScheduler, observableScheduler)
    }

}