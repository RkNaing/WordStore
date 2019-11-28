/*
 * Copyright (C) 2019 WordsStore
 *
 * Created by		:	Rahul Kumar
 * Last Modified	:	28 Nov 2019 03:06:11 PM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

@file:Suppress("unused")

package com.devshub.rk.wordsstore.utils

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager


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