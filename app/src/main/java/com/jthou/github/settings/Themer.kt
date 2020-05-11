package com.jthou.github.settings

import android.app.Activity
import androidx.annotation.StyleRes
import com.jthou.github.R

object Themer {

    enum class ThemeMode(@StyleRes val styleRes: Int, @StyleRes val translucent: Int) {
        DAY(R.style.AppTheme, R.style.AppTheme_Translucent),
        NIGHT(R.style.AppTheme_Dark, R.style.AppTheme_Dark_Translucent)
    }

    fun applyProperTheme(activity: Activity, translucent: Boolean = false) {
        activity.setTheme(currentTheme().let {
            if (translucent) {
                it.translucent
            } else {
                it.styleRes
            }
        })
    }

    fun currentTheme() : ThemeMode = ThemeMode.valueOf(Settings.themeMode)

    fun toggle(activity: Activity) {
        when (currentTheme()) {
            ThemeMode.DAY -> Settings.themeMode = ThemeMode.NIGHT.name
            ThemeMode.NIGHT -> Settings.themeMode = ThemeMode.DAY.name
        }
        activity.recreate()
    }

}