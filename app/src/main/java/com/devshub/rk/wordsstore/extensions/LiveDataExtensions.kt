package com.devshub.rk.wordsstore.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Created by ZMN on 1/3/19.
 **/

fun <T> LiveData<T>.observeOnce(observer: Observer<T>) {
    observeForever(object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}