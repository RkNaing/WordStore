package com.devshub.rk.wordsstore.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by ZMN on 12/7/18.
 **/
@Entity(tableName = "WORD_TBL")
@Parcelize
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var title: String,
    var description: String
):Parcelable