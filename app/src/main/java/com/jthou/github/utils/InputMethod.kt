package com.jthou.github.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import splitties.systemservices.inputMethodManager

fun Context.toggleSoftInput() {
    inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
}

fun View.showSoftInput(): Boolean {
    return inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_FORCED)
}

fun Activity.showSoftInput(): Boolean {
    return currentFocus?.showSoftInput() ?: false
}

fun View.hideSoftInput(): Boolean {
    return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun Activity.hideSoftInput(): Boolean {
    return currentFocus?.hideSoftInput() ?: false
}

fun Context.isActive(): Boolean {
    return inputMethodManager.isActive
}