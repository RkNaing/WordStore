package com.devshub.rk.wordsstore.extensions

import android.util.Log
import com.devshub.rk.wordsstore.BuildConfig

/**
 * Created by ZMN on 12/21/18.
 **/


private val Any.logTag: String
    get() {
        val className = this::class.java.simpleName
        return if (className.length > 25) className.substring(0, 26) else className
    }

fun Any.logd(message: String?) {
    if (BuildConfig.DEBUG) Log.d(logTag, message)
}