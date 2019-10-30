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

/**
 * Created by ZMN on 2019-10-30.
 **/

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