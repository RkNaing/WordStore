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
                        logo = R.drawable.welcome_slide_one_lg
                    )
                )

                it.add(
                    WelcomeTopic(
                        title = R.string.welcome_slide_two_title,
                        description = R.string.welcome_slide_two_desc,
                        logo = R.drawable.welcome_slide_two_lg
                    )
                )

                it.add(
                    WelcomeTopic(
                        title = R.string.welcome_slide_three_title,
                        description = R.string.welcome_slide_three_desc,
                        logo = R.drawable.welcome_slide_three_lg
                    )
                )

                it.add(
                    WelcomeTopic(
                        title = R.string.welcome_slide_four_title,
                        description = R.string.welcome_slide_four_desc,
                        logo = R.drawable.welcome_slide_four_lg
                    )
                )
            }
        }
    }
}