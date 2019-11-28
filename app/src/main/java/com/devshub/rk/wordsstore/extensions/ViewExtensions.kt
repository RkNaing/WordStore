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

package com.devshub.rk.wordsstore.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


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

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun AppCompatTextView.setTextAsync(text: CharSequence?) {
    val textMetricsParams = TextViewCompat.getTextMetricsParams(this)
    val futureParam = PrecomputedTextCompat.getTextFuture(text ?: "", textMetricsParams, null)
    setTextFuture(futureParam)
}