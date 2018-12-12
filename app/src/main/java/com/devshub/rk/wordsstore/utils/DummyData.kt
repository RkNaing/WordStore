package com.devshub.rk.wordsstore.utils

import android.content.Context
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.data.model.Category
import com.devshub.rk.wordsstore.data.model.Word

/**
 * Created by ZMN on 12/11/18.
 **/

fun getDummyCategories(): MutableList<Category> {

    val dummyCategories: MutableList<Category> = mutableListOf()

    for (i in 1..25) {
        dummyCategories.add(
            Category(title = "Category $i", description = "Category Description for Category $i")
        )
    }

    return dummyCategories
}


fun getDummyWords(context: Context): MutableList<Word> {

    val dummyWords: MutableList<Word> = mutableListOf()

    for (i in 1..100) {
        dummyWords.add(
            Word(
                title = "Word $i",
                description = context.getString(R.string.textWordDesc)
            )
        )
    }

    return dummyWords
}