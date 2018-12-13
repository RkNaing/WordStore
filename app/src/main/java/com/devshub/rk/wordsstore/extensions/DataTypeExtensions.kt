package com.devshub.rk.wordsstore.extensions

import android.os.Build
import android.text.Html
import android.text.Spanned

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