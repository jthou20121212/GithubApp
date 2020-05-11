package com.jthou.github.utils

import android.content.Context
import android.provider.Settings

/**
 *
 *
 * @author jthou
 * @version 1.0.0
 * @date 01-03-2020
 */
val Context.deviceId: String
    get() = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)