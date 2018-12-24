package com.devshub.rk.wordsstore.app

import android.app.Application
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
    }

}