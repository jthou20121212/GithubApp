package com.jthou.github.utils

import com.google.gson.Gson

/**
 *
 *
 * @author jthou
 * @version 1.0.0
 * @date 01-03-2020
 */
inline fun <reified T> Gson.fromGson(json: String) : T = Gson().fromJson(json, T::class.java)