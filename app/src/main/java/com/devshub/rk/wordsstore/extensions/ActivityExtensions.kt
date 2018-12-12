package com.devshub.rk.wordsstore.extensions

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

/**
 * Created by ZMN on 12/11/18.
 **/

fun Activity.dismissSoftKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    currentFocus?.let { view ->
        inputMethodManager?.hideSoftInputFromWindow(view.applicationWindowToken, 0)
    }
}