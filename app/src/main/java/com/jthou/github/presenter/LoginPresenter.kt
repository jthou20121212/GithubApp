package com.jthou.github.presenter

import com.jthou.github.BuildConfig
import com.jthou.github.model.account.AccountManager
import com.jthou.github.view.LoginActivity
import com.jthou.mvp.BasePresenter

/**
 *
 *
 * @author jthou
 * @version 1.0.0
 * @date 02-03-2020
 */
class LoginPresenter : BasePresenter<LoginActivity>() {

    fun doLogin(username: String, password: String) {
        AccountManager.username = username
        AccountManager.password = password
        view.onLoginStart()
        AccountManager
            .login()
            .subscribe({
                view.onLoginSuccess()
            }, {
                view.onLoginFail(it)
            })
    }

    fun checkUsername(username: String): Boolean {
        return true
    }

    fun checkPassword(password: String): Boolean {
        return true
    }

    override fun onResume() {
        super.onResume()
        if (BuildConfig.DEBUG) {
            view.onDataInit(BuildConfig.testUserName, BuildConfig.testPassword)
        } else {
            view.onDataInit(AccountManager.username, AccountManager.password)
        }
    }

}