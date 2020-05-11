package com.jthou.github.model.account

import com.google.gson.Gson
import com.jthou.github.network.entites.AuthorizationReq
import com.jthou.github.network.entites.AuthorizationRsp
import com.jthou.github.network.entites.User
import com.jthou.github.network.services.AuthService
import com.jthou.github.network.services.UserService
import com.jthou.github.utils.fromGson
import com.jthou.github.utils.pref
import retrofit2.HttpException
import rx.Observable
import java.util.*

/**
 *
 *
 * @author jthou
 * @version 1.0.0
 * @date 01-03-2020
 */

object AccountManager {

    var username by pref("")
    var password by pref("")
    var token by pref("")
    var authId by pref(-1)

    private var userJson by pref("")

    var currentUser: User? = null
        get() {
            if (field == null && userJson.isNotEmpty()) {
                field = Gson().fromGson(userJson)
            }
            return field
        }
        set(value) {
            userJson = if (value == null) {
                ""
            } else {
                Gson().toJson(value)
            }
            field = value
        }

    val onAccountStateChangeListeners = ArrayList<OnAccountStateChangeListener>()

    private fun notifyLogin(user: User) {
        onAccountStateChangeListeners.forEach {
            it.onLogin(user)
        }
    }

    private fun notifyLogout() {
        onAccountStateChangeListeners.forEach {
            it.onLogout()
        }
    }

    fun isLoggedIn(): Boolean = token.isNotEmpty()

    fun login() = AuthService
        .createAuthorization(AuthorizationReq())
        .doOnNext {
            if (it.token.isEmpty()) {
                throw AccountException(it)
            }
        }
        .retryWhen {
            it.flatMap {
                if (it is AccountException) {
                    AuthService.deleteAuthorization(it.authorizationRsp.id)
                } else {
                    Observable.error(it)
                }
            }
        }
        .flatMap {
            token = it.token
            authId = it.id
            UserService.getAuthenticatedUser()
        }
        .map {
            currentUser = it
            notifyLogin(it)
        }

    fun logout() =
        AuthService
            .deleteAuthorization(authId)
            .doOnNext {
                if (it.isSuccessful) {
                    authId = -1
                    token = ""
                    currentUser = null
                    notifyLogout()
                } else {
                    throw HttpException(it)
                }
            }


    class AccountException(val authorizationRsp: AuthorizationRsp) : Exception("Already logged in.")

    interface OnAccountStateChangeListener {

        fun onLogin(user: User)

        fun onLogout()

    }

}