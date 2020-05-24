package com.jthou.github.view.widget

import android.content.Context
import org.jetbrains.anko.alert
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun Context.confirm(title: String, message: String = "") =
    suspendCoroutine<Boolean> { continuation ->
        alert {
            this.title = title
            this.message = message

            negativeButton("Cancel") {
                continuation.resume(false)
            }
            positiveButton("Ok") {
                continuation.resume(true)
            }

            onCancelled {
                continuation.resume(false)
            }
        }.show()
    }