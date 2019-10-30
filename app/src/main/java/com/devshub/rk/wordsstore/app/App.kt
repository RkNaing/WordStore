package com.devshub.rk.wordsstore.app

import android.app.Application
import com.devshub.rk.wordsstore.utils.PREF_THEME
import com.devshub.rk.wordsstore.utils.PreferenceHelper
import com.devshub.rk.wordsstore.utils.ThemeHelper
import timber.log.Timber

/**
 * Created by ZMN on 12/13/18.
 **/
class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Timber.plant(Timber.DebugTree())

        val selectedTheme =
            PreferenceHelper.getInstance(this).getStringPref(PREF_THEME, ThemeHelper.DEFAULT_MODE)
        ThemeHelper.applyTheme(selectedTheme)
    }

}