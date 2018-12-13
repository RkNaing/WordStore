package com.devshub.rk.wordsstore.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import es.dmoral.toasty.Toasty

/**
 * Created by ZMN on 12/13/18.
 **/

fun Context.infoToast(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toasty.info(this, message, duration).show()
}

fun Context.successToast(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toasty.success(this, message, duration).show()
}

fun Context.errorToast(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toasty.error(this, message, duration).show()
}