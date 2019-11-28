/*
 * Copyright (C) 2019 WordsStore
 *
 * Created by		:	Rahul Kumar
 * Last Modified	:	28 Nov 2019 03:06:10 PM
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
    fun getWordsCount(context: Context): Int

    fun getAllWordsWithCategoryTitlePagedList(context: Context): LiveData<PagedList<WordWithCategory>>

    fun getAllWordsByCategoryIDPagedList(
        context: Context,
        categoryId: Long
    ): LiveData<PagedList<WordWithCategory>>

    fun getSingleRandomWordWithCategoryTitle(
        context: Context,
        wordWithCategoryCallback: WordWithCategoryCallback
    )

    @WorkerThread
    fun getSingleRandomWordWithCategoryTitle(context: Context): WordWithCategory

    fun getWordWithCategoryById(context: Context, id: Long): LiveData<WordWithCategory?>
}