package com.devshub.rk.wordsstore.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.devshub.rk.wordsstore.data.model.Word
import com.devshub.rk.wordsstore.data.model.WordWithCategory
import com.devshub.rk.wordsstore.utils.CompletionCallback

interface WordRepository {

    fun addWord(context: Context, word: Word, completion: CompletionCallback)

    fun updateWord(context: Context, word: Word, completion: CompletionCallback)

    fun deleteWord(context: Context, word: Word, completion: CompletionCallback)

    fun getAllWordsWithCategoryTitlePagedList(context: Context): LiveData<PagedList<WordWithCategory>>
}