package com.jthou.github.utils

import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

inline fun DrawerLayout.afterClose(crossinline block: () -> Unit) {
    if (isDrawerOpen(GravityCompat.START)) {
        closeDrawer(GravityCompat.START)
        addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) = Unit
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) = Unit
            override fun onDrawerOpened(drawerView: View) = Unit
            override fun onDrawerClosed(drawerView: View) {
                removeDrawerListener(this)
                block()
            }
        })
    } else {
        block()
    }
}