package com.devshub.rk.wordsstore.data.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.devshub.rk.wordsstore.R
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
                it.add(
                    WelcomeTopic(
                        title = R.string.welcome_side_one_title,
                        description = R.string.welcome_side_one_desc,
                        logo = R.drawable.app_logo_256
                    )
                )

                it.add(
                    WelcomeTopic(
                        title = R.string.welcome_slide_two_title,
                        description = R.string.welcome_slide_two_desc,
                        logo = R.drawable.features
                    )
                )

                it.add(
                    WelcomeTopic(
                        title = R.string.welcome_slide_three_title,
                        description = R.string.welcome_slide_three_desc,
                        logo = R.drawable.widget_preview
                    )
                )

                it.add(
                    WelcomeTopic(
                        title = R.string.welcome_slide_four_title,
                        description = R.string.welcome_slide_four_desc,
                        logo = R.drawable.devshub_logo
                    )
                )
            }
        }
    }
}