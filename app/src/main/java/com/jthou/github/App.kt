package com.jthou.github

import android.app.Application
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.bennyhuo.swipefinishable.SwipeFinishable

/**
 *
 *
 * @author jthou
 * @version 1.1.0
 * @date 01-03-2020
 */
private lateinit var INSTANCE : Application

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        SwipeFinishable.INSTANCE.init(this)
    }

}

object AppContext : ContextWrapper(INSTANCE)