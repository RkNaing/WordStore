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

import android.app.Activity
import android.content.Context
import android.text.Spanned
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.devshub.rk.wordsstore.R


fun Activity.dismissSoftKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    currentFocus?.let { view ->
        inputMethodManager?.hideSoftInputFromWindow(view.applicationWindowToken, 0)
    }
}

fun Activity.showDeleteConfirmDialog(message: Spanned, onDeleteCallback: () -> Unit) {
    val dialog = AlertDialog.Builder(this)
        .setTitle(R.string.lbl_delete_confirm)
        .setMessage(message)
        .setPositiveButton(R.string.action_delete) { dialog, _ ->
            onDeleteCallback()
            dialog.dismiss()
        }
        .setNegativeButton(R.string.action_cancel) { dialog, _ ->
            dialog.dismiss()
        }
        .create()
    dialog.setOnShowListener {
        with(dialog.getButton(AlertDialog.BUTTON_POSITIVE)) {
            setTextColor(
                ContextCompat.getColor(
                    this@showDeleteConfirmDialog,
                    android.R.color.holo_red_light
                )
            )
            isAllCaps = false
        }
        with(dialog.getButton(AlertDialog.BUTTON_NEGATIVE)) {
            setTextColor(ContextCompat.getColor(dialog.context, R.color.colorTextSecondary))
            isAllCaps = false
        }
    }
    dialog.show()
}