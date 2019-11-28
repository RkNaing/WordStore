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

package com.devshub.rk.wordsstore.utils

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate

object ThemeHelper {

    const val LIGHT_MODE = "light"
    const val DARK_MODE = "dark"
    const val DEFAULT_MODE = "default"

    var isDarkTheme = false

    fun applyTheme(theme: String) {
        val mode = when (theme) {
            LIGHT_MODE -> AppCompatDelegate.MODE_NIGHT_NO
            DARK_MODE -> AppCompatDelegate.MODE_NIGHT_YES
            else -> {
                when {
                    Build.VERSION.SDK_INT >= 28 -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    Build.VERSION.SDK_INT >= 21 -> AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
                    else -> AppCompatDelegate.MODE_NIGHT_NO
                }
            }
        }
        AppCompatDelegate.setDefaultNightMode(mode)
        isDarkTheme = theme == DARK_MODE
    }


}