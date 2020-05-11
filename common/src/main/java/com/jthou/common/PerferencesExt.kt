package com.jthou.common

import android.content.Context
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 *
 *
 * @author jthou
 * @version 1.1.0
 * @date 29-02-2020
 */


class Preference<T>(private val context: Context, private val key: String, private val default: T, private val prefName: String = "default")
    : ReadWriteProperty<Any?, T> {

    private val prefs by lazy {
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(findProperName(property))
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(findProperName(property), value)
    }

    private fun findProperName(property: KProperty< *>): String {
       return if (key.isEmpty()) property.name else key
    }

    private fun findPreference(name: String): T {
       return when(default) {
            is Int -> prefs.getInt(name, default)
            is Boolean -> prefs.getBoolean(name, default)
            is Long -> prefs.getLong(name, default)
            is String -> prefs.getString(name, default)
            else -> throw IllegalArgumentException("Unsupported type.")
        } as T
    }

    private fun putPreference(name: String, value: T) {
        with(prefs.edit()) {
            when(value) {
                is Int -> putInt(name, value)
                is Boolean -> putBoolean(name, value)
                is Long -> putLong(name, value)
                is String -> putString(name, value)
                else -> throw IllegalArgumentException("Unsupported type.")
            }
        }.apply()
    }


}
