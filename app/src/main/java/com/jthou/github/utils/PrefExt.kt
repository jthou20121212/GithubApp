package com.jthou.github.utils

import com.jthou.common.Preference
import com.jthou.github.AppContext
import kotlin.reflect.jvm.jvmName

/**
 *
 *
 * @author jthou
 * @version 1.0.0
 * @date 01-03-2020
 */

inline fun <reified R, T> R.pref(default: T) = Preference(AppContext, "", default, R::class.jvmName)