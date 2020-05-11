package com.jthou.github.settings

import com.jthou.github.AppContext
import com.jthou.github.utils.deviceId

/**
 *
 *
 * @author jthou
 * @version 1.1.0
 * @date 01-03-2020
 */
object Configs  {

    object Account {
        val SCOPES = listOf("user", "repo", "notifications", "gist", "admin:org")
        const val clientId = "3f326c1761ee156981e8"
        const val clientSecret = "3f5371dede14e5bc92982aaf72373ca67c6385d2"
        const val note = "kotliner.cn"
        const val noteUrl = "http://www.kotliner.cn"

        val fingerPrint by lazy {
            (AppContext.deviceId + clientId).also {  }
        }
    }

}