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

package com.devshub.rk.wordsstore.ui.views

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.devshub.rk.wordsstore.R


class SettingItemView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.view_setting_item, this)

        val ivSettingIcon = findViewById<AppCompatImageView>(R.id.vsi_ivIcon)
        val tvTitle = findViewById<AppCompatTextView>(R.id.vsi_tvTitle)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.SettingItemView)
        ivSettingIcon.setImageDrawable(attributes.getDrawable(R.styleable.SettingItemView_vsi_icon))
        tvTitle.text = attributes.getString(R.styleable.SettingItemView_vsi_title)
        attributes.recycle()

        isClickable = true
        isFocusable = true

        if (Build.VERSION.SDK_INT >= 21) {
            context.theme?.let { theme ->
                val outVal = TypedValue()
                theme.resolveAttribute(android.R.attr.selectableItemBackground, outVal, true)
                setBackgroundResource(outVal.resourceId)
            }
        }

    }

}