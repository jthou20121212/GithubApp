package com.jthou.retroapollo_android.rxjava

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import rx.Observable
import rx.Subscriber

class CallExecuteOnSubscribe<T> constructor(val apolloCall: ApolloCall<T>) : Observable.OnSubscribe<Response<T>> {

    override fun call(t: Subscriber<in Response<T>>) {
        val callArbiter = CallArbiter(apolloCall, t)
        t.add(callArbiter)
        t.setProducer(callArbiter)
    }

}