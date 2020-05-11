package com.jthou.github.network

import splitties.systemservices.connectivityManager

object Network {

    fun isAvailable() = connectivityManager.activeNetworkInfo?.isAvailable ?: false

}