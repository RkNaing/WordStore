package com.devshub.rk.wordsstore.data.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WelcomeTopic(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val logo: Int
) : Parcelable {

    companion object {
        fun getTopics(): List<WelcomeTopic> {
            return mutableListOf<WelcomeTopic>().also {

            }
        }
    }
}