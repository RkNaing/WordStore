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

                override fun areContentsTheSame(oldItem: WordWithCategory, newItem: WordWithCategory) =
                    oldItem == newItem

            }
    }
}