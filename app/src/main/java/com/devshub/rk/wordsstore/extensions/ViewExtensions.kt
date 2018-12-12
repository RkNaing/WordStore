package com.devshub.rk.wordsstore.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

/**
 * Created by ZMN on 12/10/18.
 **/


fun ViewGroup.addSubView(@LayoutRes from: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(from, this, attachToRoot)
}