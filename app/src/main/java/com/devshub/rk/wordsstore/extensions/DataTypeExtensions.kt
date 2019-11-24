package com.devshub.rk.wordsstore.extensions

import android.os.Build
import android.text.Html
import android.text.Spanned
import com.devshub.rk.wordsstore.utils.DATE_TIME_READABLE
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ZMN on 12/13/18.
 **/

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
    }catch (e:Exception){
        Timber.e(e)
        null
    }
}

fun Calendar.toReadableString(): String = getFormattedDate(DATE_TIME_READABLE) ?: toString()