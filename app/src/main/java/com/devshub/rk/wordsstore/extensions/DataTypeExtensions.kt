/*
 * Copyright (C) 2019 WordsStore
 *
 * Created by		:	Rahul Kumar
 * Last Modified	:	28 Nov 2019 03:06:12 PM
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

import android.os.Build
import android.text.Html
import android.text.Spanned
import com.devshub.rk.wordsstore.utils.DATE_TIME_READABLE
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
fun String.getHtml(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}

fun String.getDate(format: String): Date? {
    return try {
        val simpleDateFormat = SimpleDateFormat(format, Locale.US)
        simpleDateFormat.parse(this)
    } catch (e: Exception) {
        Timber.e(e)
        null
    }
}

fun Calendar.getFormattedDate(format: String): String? {
    return try {
        val simpleDateFormat = SimpleDateFormat(format, Locale.US)
        simpleDateFormat.format(time)
    } catch (e: Exception) {
        Timber.e(e)
        null
    }
}

fun Calendar.toReadableString(): String = getFormattedDate(DATE_TIME_READABLE) ?: toString()