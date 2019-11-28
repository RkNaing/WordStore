/*
 * Copyright (C) 2019 WordsStore
 *
 * Created by		:	Rahul Kumar
 * Last Modified	:	28 Nov 2019 03:06:10 PM
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

package com.devshub.rk.wordsstore.app

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import com.devshub.rk.wordsstore.BuildConfig
import com.devshub.rk.wordsstore.utils.PREF_THEME
import com.devshub.rk.wordsstore.utils.PreferenceHelper
import com.devshub.rk.wordsstore.utils.ThemeHelper
import timber.log.Timber

class App : Application(), Configuration.Provider {

    companion object {

        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        val selectedTheme =
            PreferenceHelper.getInstance(this).getStringPref(PREF_THEME, ThemeHelper.DARK_MODE)
        ThemeHelper.applyTheme(selectedTheme)
        Timber.d("onCreate")
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().setMinimumLoggingLevel(Log.VERBOSE).build()
    }

}
