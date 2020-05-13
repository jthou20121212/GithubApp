package com.jthou.retroapollo_android

import com.apollographql.apollo.api.Query
import com.jthou.retroapollo_android.annotations.GraphQLQuery
import com.jthou.retroapollo_android.utils.Utils
import com.jthou.retroapollo_android.utils.error
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

class ApolloServiceMethod<T : Any>(private val retroApollo: RetroApollo,
                                   val method: Method,
                                   private val buildBuilderMethod: Method,
                                   private val buildQueryMethod: Method,
                                   private val fieldSetters: List<Method>,
                                   private val callAdapter: CallAdapter<Any, T>
) {

    class Builder(private val retroApollo: RetroApollo, val method: Method){

        private val callAdapter: CallAdapter<Any, Any>
        private val buildBuilderMethod: Method
        private val buildQueryMethod: Method
        private val fieldSetters = ArrayList<Method>()

        init {
            val returnType = method.genericReturnType
            if(Utils.hasUnresolvableType(returnType)){
                throw method.error("Method return type must not include a type variable or wildcard: %s", returnType)
            }
            if(returnType === Void.TYPE){
                throw method.error("Service methods cannot return void.")
            }
            if(returnType !is ParameterizedType){
                val name = (returnType as Class<*>).simpleName
                throw IllegalStateException("$name return type must be parameterized as $name<Foo> or $name<out Foo>")
            }

            callAdapter = retroApollo.getCallAdapter(returnType) ?: throw  IllegalStateException("$returnType is not supported.")

            //RepositoryIssueCountQuery.Data.class
            val dataType = callAdapter.responseType() as Class<*>

            val enclosingClass = dataType.enclosingClass!!
            buildBuilderMethod = enclosingClass.getDeclaredMethod("builder")
            val builderClass = enclosingClass.declaredClasses.first { it.simpleName == "Builder" }

            method.parameterAnnotations.zip(method.parameterTypes).mapTo(fieldSetters){
                    (first, second) ->
                val annotation = first.first { it is GraphQLQuery } as GraphQLQuery
                builderClass.getDeclaredMethod(annotation.value, second)
            }

            buildQueryMethod = builderClass.getDeclaredMethod("build")
        }

        fun build() = ApolloServiceMethod(retroApollo, method, buildBuilderMethod, buildQueryMethod, fieldSetters, callAdapter)

    }

    operator fun invoke(args: Array<Any>?): T {
        val builder = buildBuilderMethod(null)
        args?.let {
            fieldSetters.zip(it).forEach {
                it.first.invoke(builder, it.second)
            }
        }

        //RepositoryIssueCountQuery.builder().owner(xxx).repo(xxx).build()
        return callAdapter.adapt(retroApollo.apolloClient.query(buildQueryMethod(builder) as Query<*, Any, *>))
    }

}