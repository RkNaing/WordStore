package com.devshub.rk.wordsstore.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WordWithCategory(
    @Embedded
    var word: Word,

    @ColumnInfo(name = "category_title")
    var categoryTitle: String
) : Parcelable