package com.jthou.github.view

import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.jthou.common.otherwise
import com.jthou.common.yes
import com.jthou.github.R
import com.jthou.github.presenter.LoginPresenter
import com.jthou.github.settings.Themer
import com.jthou.github.utils.hideSoftInput
import com.jthou.mvp.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import splitties.activities.start
import splitties.toast.toast


/**
 * @author jthou
 * @version 1.0.0
 * @date 01-03-2020
 */
class LoginActivity : BaseActivity<LoginPresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Themer.applyProperTheme(this)
        setContentView(R.layout.activity_login)
        login.setOnClickListener {
            val username = username.text.toString()
            val password = password.text.toString()
            presenter.checkUsername(username)
                .yes {
                    presenter.checkPassword(password)
                        .yes {
                            hideSoftInput()
                            presenter.doLogin(username, password)
                        }.otherwise {
                            showTips(this.password, "密码不合法")
                        }
                }.otherwise {
                    showTips(this.username, "用户名不合法")
                }
        }
    }

    private fun showTips(view: EditText, error: String) {
        view.requestFocus()
        view.error = error
    }

    fun onLoginStart() {
        loading?.visibility = View.VISIBLE
    }

    fun onLoginFail(e: Throwable) {
        e.printStackTrace()
        loading?.visibility = View.GONE
        toast("登录失败")
    }

    fun onLoginSuccess() {
        loading?.visibility = View.GONE
        start<MainActivity>()
    }

    fun onDataInit(name: String, pwd: String) {
        username.setText(name)
        password.setText(pwd)
    }

}