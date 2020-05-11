package com.jthou.github.utils

import kotlin.math.roundToInt

fun Int.toKilo(): String {
    return if (this > 700) {
        "${((this / 100f).roundToInt() / 10f)}k"
    } else {
        "$this"
    }
}