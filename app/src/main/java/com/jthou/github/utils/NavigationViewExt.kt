package com.jthou.github.utils

import android.annotation.SuppressLint
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.view.menu.MenuItemImpl
import androidx.core.view.ViewCompat
import com.google.android.material.navigation.NavigationView
import com.jthou.common.otherwise
import com.jthou.common.yes

inline fun NavigationView.doOnLayoutAvailable(crossinline block: () -> Unit) {
    ViewCompat
        .isLaidOut(this)
        .yes {
            block()
        }
        .otherwise {
            addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                override fun onLayoutChange(
                    v: View?,
                    left: Int,
                    top: Int,
                    right: Int,
                    bottom: Int,
                    oldLeft: Int,
                    oldTop: Int,
                    oldRight: Int,
                    oldBottom: Int
                ) {
                    removeOnLayoutChangeListener(this)
                    block()
                }
            })
        }
}

@SuppressLint("RestrictedApi")
fun NavigationView.selectItem(@IdRes resId: Int) {
    doOnLayoutAvailable {
        setCheckedItem(resId)
        (menu.findItem(resId) as MenuItemImpl)()
    }
}

