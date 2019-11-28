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
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Embedded
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WordWithCategory(
    @Embedded
    val word: Word,

    @Embedded(prefix = "word_category_")
    val wordCategory: Category
) : Parcelable {
    companion object {
        val diffUtilCallback: DiffUtil.ItemCallback<WordWithCategory> =
            object : DiffUtil.ItemCallback<WordWithCategory>() {
                override fun areItemsTheSame(oldItem: WordWithCategory, newItem: WordWithCategory) =
                    oldItem.word.id == newItem.word.id

                override fun areContentsTheSame(
                    oldItem: WordWithCategory,
                    newItem: WordWithCategory
                ) =
                    oldItem == newItem

            }
    }
}