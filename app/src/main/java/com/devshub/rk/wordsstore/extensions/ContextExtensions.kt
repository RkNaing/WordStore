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

@file:Suppress("unused")

package com.devshub.rk.wordsstore.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import es.dmoral.toasty.Toasty

fun Context.infoToast(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toasty.info(this, message, duration).show()
}

fun Context.infoToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toasty.info(this, message, duration).show()
}

fun Context.successToast(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toasty.success(this, message, duration).show()
}

fun Context.successToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toasty.success(this, message, duration).show()
}

fun Context.errorToast(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toasty.error(this, message, duration).show()
}

fun Context.errorToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toasty.error(this, message, duration).show()
}