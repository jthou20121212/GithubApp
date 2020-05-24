package com.jthou.github.network

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.jthou.github.network.graphql.entities.RepositoryIssueCountQuery
import com.jthou.github.network.interceptors.AuthInterceptor
import com.jthou.retroapollo_android.RetroApollo
import com.jthou.retroapollo_android.annotations.GraphQLQuery
import com.jthou.retroapollo_android.rxjava.RxJavaCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

private const val BASE_URL = "https://api.github.com/graphql"

interface GraphQLApi {

    fun queryIssuesCount(
        @GraphQLQuery("owner") owner: String,
        @GraphQLQuery("repo") repo: String
    ): Observable<RepositoryIssueCountQuery.Data>

    fun queryIssuesCount2(
        @GraphQLQuery("owner") owner: String,
        @GraphQLQuery("repo") repo: String
    ): ApolloCall<RepositoryIssueCountQuery.Data>

}

val apolloClient by lazy {
    ApolloClient
        .builder()
        .serverUrl(BASE_URL)
        .okHttpClient(
            OkHttpClient
                .Builder()
                .addInterceptor(AuthInterceptor())
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                }).build()
        )
        .build()
}

private val graphQLService by lazy {
    RetroApollo
        .Builder()
        .apolloClient(apolloClient)
        .addCallAdapterFactory(
            RxJavaCallAdapterFactory()
                .subscribeScheduler(Schedulers.io())
                .observableScheduler(AndroidSchedulers.mainThread())
        )
        .build()
        .createGraphQLService(GraphQLApi::class)
}

object GraphQLService : GraphQLApi by graphQLService