package com.devshub.rk.wordsstore.data.repositories

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.devshub.rk.wordsstore.data.model.Word
import com.devshub.rk.wordsstore.data.model.WordWithCategory
import com.devshub.rk.wordsstore.utils.CompletionCallback
import com.devshub.rk.wordsstore.utils.WordWithCategoryCallback

interface WordRepository {

    fun addWord(context: Context, word: Word, completion: CompletionCallback)

    fun updateWord(context: Context, word: Word, completion: CompletionCallback)

    fun deleteWord(context: Context, word: Word, completion: CompletionCallback)

    fun getWordsCount(context: Context, completion: (Int) -> Unit)

    @WorkerThread
    fun getWordsCount(context: Context):Int

    fun getAllWordsWithCategoryTitlePagedList(context: Context): LiveData<PagedList<WordWithCategory>>

    fun getAllWordsByCategoryIDPagedList(context: Context, categoryId: Long): LiveData<PagedList<WordWithCategory>>

    fun getSingleRandomWordWithCategoryTitle(context: Context, wordWithCategoryCallback: WordWithCategoryCallback)

    @WorkerThread
    fun getSingleRandomWordWithCategoryTitle(context: Context):WordWithCategory

    fun getWordWithCategoryById(context: Context, id: Long): LiveData<WordWithCategory?>
}