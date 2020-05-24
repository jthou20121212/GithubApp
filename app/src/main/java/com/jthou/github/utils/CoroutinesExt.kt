package com.jthou.github.utils

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

fun GlobalScope.launchMain(
    context: CoroutineContext = Dispatchers.Main,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = GlobalScope.launch(context, start, block)

suspend fun <T> resultOrError(block: () -> T): com.jthou.github.network.entites.Result<T> =
    try {
        com.jthou.github.network.entites.Result.of(block())
    } catch (e: Throwable) {
        com.jthou.github.network.entites.Result.of(e)
    }
