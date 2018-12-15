package com.devshub.rk.wordsstore.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * Created by ZMN on 12/10/18.
 **/


fun ViewGroup.addSubView(@LayoutRes from: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(from, this, attachToRoot)
}


val TextInputEditText.trimmedText: String
    get() = this.text?.toString()?.trim() ?: ""

fun TextInputLayout.showError(@StringRes message: Int) {
    isErrorEnabled = true
    error = context.getString(message)
}

fun TextInputLayout.clearError() {
    isErrorEnabled = false
    error = null
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible(){
    this.visibility = View.VISIBLE
}