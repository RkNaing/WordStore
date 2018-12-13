package com.devshub.rk.wordsstore.extensions

import android.app.Activity
import android.content.Context
import android.text.Spanned
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import com.devshub.rk.wordsstore.R

/**
 * Created by ZMN on 12/11/18.
 **/

fun Activity.dismissSoftKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    currentFocus?.let { view ->
        inputMethodManager?.hideSoftInputFromWindow(view.applicationWindowToken, 0)
    }
}

fun Activity.showDeleteConfirmDialog(message: Spanned, onDeleteCallback: () -> Unit) {
    AlertDialog.Builder(this, R.style.DeleteConfirmAlertDialogStyle)
        .setTitle(R.string.lbl_delete_confirm)
        .setMessage(message)
        .setPositiveButton(R.string.action_delete) { dialog, _ ->
            onDeleteCallback()
            dialog.dismiss()
        }
        .setNegativeButton(R.string.action_cancel) { dialog, _ ->
            dialog.dismiss()
        }
        .show()
}