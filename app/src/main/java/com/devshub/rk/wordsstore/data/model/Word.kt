package com.devshub.rk.wordsstore.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by ZMN on 12/7/18.
 **/
@Entity(tableName = "WORD_TBL")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var title: String,
    var description: String
)