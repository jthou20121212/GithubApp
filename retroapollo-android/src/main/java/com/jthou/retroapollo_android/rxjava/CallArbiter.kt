package com.jthou.retroapollo_android.rxjava

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import rx.Producer
import rx.Subscriber
import rx.Subscription
import rx.exceptions.*
import rx.plugins.RxJavaPlugins
import java.util.concurrent.atomic.AtomicInteger

class CallArbiter<T>(val apolloCall: ApolloCall<T>, val subscriber: Subscriber<in Response<T>>) :
    Subscription, Producer {

    companion object {
        private const val STATE_WAITING = 0
        private const val STATE_REQUESTED = 1
        private const val STATE_TERMINATED = 2
    }

    val atomicState = AtomicInteger(STATE_WAITING)

    override fun isUnsubscribed(): Boolean {
     return apolloCall.isCanceled
    }

    override fun unsubscribe() {
        apolloCall.cancel()
    }

    override fun request(n: Long) {
       if (n == 0L) {
           return
       }

       while (true) {
           val state = atomicState.get()
           when (state) {
               STATE_WAITING -> if (atomicState.compareAndSet(STATE_WAITING, STATE_REQUESTED)) {
                   apolloCall.enqueue(object : ApolloCall.Callback<T>() {
                       override fun onFailure(e: ApolloException) {
                           Exceptions.throwIfFatal(e)
                           emitError(e)
                       }

                       override fun onResponse(response: Response<T>) {
                           if (atomicState.compareAndSet(STATE_REQUESTED, STATE_TERMINATED)) {
                               deliverResponse(response)
                           }
                       }
                   })
                   return
               }
               STATE_REQUESTED, STATE_TERMINATED -> return
           }
       }

















    }

    fun deliverResponse(response: Response<T>) {
        try {
            if (isUnsubscribed) {
                return
            }

            subscriber.onNext(response)
        } catch (e: OnCompletedFailedException) {
            RxJavaPlugins.getInstance().errorHandler.handleError(e)
            return
        } catch (e: OnErrorFailedException) {
            RxJavaPlugins.getInstance().errorHandler.handleError(e)
            return
        } catch (e: OnErrorNotImplementedException) {
            RxJavaPlugins.getInstance().errorHandler.handleError(e)
            return
        } catch (t: Throwable) {
            Exceptions.throwIfFatal(t)
            try {
                subscriber.onError(t)
            } catch (e: OnCompletedFailedException) {
                RxJavaPlugins.getInstance().errorHandler.handleError(e)
            } catch (e: OnErrorFailedException) {
                RxJavaPlugins.getInstance().errorHandler.handleError(e)
            } catch (e: OnErrorNotImplementedException) {
                RxJavaPlugins.getInstance().errorHandler.handleError(e)
            } catch (inner: Throwable) {
                Exceptions.throwIfFatal(inner)
                val composite = CompositeException(t, inner)
                RxJavaPlugins.getInstance().errorHandler.handleError(composite)
            }

            return
        }

        try {
            if (isUnsubscribed) {
                return
            }

            subscriber.onCompleted()
        } catch (e: OnCompletedFailedException) {
            RxJavaPlugins.getInstance().errorHandler.handleError(e)
        } catch (e: OnErrorFailedException) {
            RxJavaPlugins.getInstance().errorHandler.handleError(e)
        } catch (e: OnErrorNotImplementedException) {
            RxJavaPlugins.getInstance().errorHandler.handleError(e)
        } catch (t: Throwable) {
            Exceptions.throwIfFatal(t)
            RxJavaPlugins.getInstance().errorHandler.handleError(t)
        }
    }

    fun emitError(e: Throwable) {
        atomicState.set(STATE_TERMINATED)

        if (isUnsubscribed) {
            return
        }

        try {
            subscriber.onError(e)
        } catch (e: OnCompletedFailedException) {
            RxJavaPlugins.getInstance().errorHandler.handleError(e)
        } catch (e: OnErrorFailedException) {
            RxJavaPlugins.getInstance().errorHandler.handleError(e)
        } catch (e: OnErrorNotImplementedException) {
            RxJavaPlugins.getInstance().errorHandler.handleError(e)
        } catch (inner : Throwable) {
            Exceptions.throwIfFatal(inner)
            val composite = CompositeException(e, inner)
            RxJavaPlugins.getInstance().errorHandler.handleError(composite)
        }

    }




















}