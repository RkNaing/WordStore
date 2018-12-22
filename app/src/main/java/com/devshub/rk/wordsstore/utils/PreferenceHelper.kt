@file:Suppress("unused")

package com.devshub.rk.wordsstore.utils

import android.content.Context
import android.preference.PreferenceManager
import androidx.core.content.edit

/**
 * Created by ZMN on 11/28/18.
 **/

class PreferenceHelper private constructor(context: Context) {

    companion object : SingletonHolder<PreferenceHelper, Context>(::PreferenceHelper)

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    // String

    fun getStringPref(key: String, defaultValue: String = "") =
        sharedPreferences.getString(key, defaultValue) ?: defaultValue

    fun setStringPref(key: String, value: String?) =
        sharedPreferences.edit { putString(key, value) }

    // Integer

    fun getIntPref(key: String, defaultValue: Int = 0) = sharedPreferences.getInt(key, defaultValue)

    fun setIntPref(key: String, value: Int) =
        sharedPreferences.edit { putInt(key, value) }

    // Long

    fun getLongPref(key: String, defaultValue: Long = 0L) =
        sharedPreferences.getLong(key, defaultValue)

    fun setLongPref(key: String, value: Long) = sharedPreferences.edit { putLong(key, value) }

    // Float

    fun getFloatPref(key: String, defaultValue: Float = 0.0F) =
        sharedPreferences.getFloat(key, defaultValue)

    fun setFloatPref(key: String, value: Float) = sharedPreferences.edit { putFloat(key, value) }

    // Boolean

    fun getBooleanPref(key: String, defaultValue: Boolean = false) =
        sharedPreferences.getBoolean(key, defaultValue)

    fun setBooleanPref(key: String, value: Boolean) =
        sharedPreferences.edit { putBoolean(key, value) }

    // remove
    fun removeKey(key: String) = sharedPreferences.edit { remove(key) }

    // Clear

    fun clearPref() = sharedPreferences.edit { clear() }

}