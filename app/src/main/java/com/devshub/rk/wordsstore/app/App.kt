package com.devshub.rk.wordsstore.app

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import com.devshub.rk.wordsstore.BuildConfig
import com.devshub.rk.wordsstore.utils.PREF_THEME
import com.devshub.rk.wordsstore.utils.PreferenceHelper
import com.devshub.rk.wordsstore.utils.ThemeHelper
import timber.log.Timber

/**
 * Created by ZMN on 12/13/18.
 **/
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
            PreferenceHelper.getInstance(this).getStringPref(PREF_THEME, ThemeHelper.DEFAULT_MODE)
        ThemeHelper.applyTheme(selectedTheme)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().setMinimumLoggingLevel(Log.VERBOSE).build()
    }

}
