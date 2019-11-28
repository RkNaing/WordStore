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

package com.devshub.rk.wordsstore.ui.viewmodels

import android.app.Application
import androidx.annotation.IdRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.data.model.Category
import com.devshub.rk.wordsstore.data.model.WordWithCategory
import com.devshub.rk.wordsstore.data.repositories.categoryRepository
import com.devshub.rk.wordsstore.data.repositories.wordRepository
import com.devshub.rk.wordsstore.utils.PREF_WIDGET_WORD_ID
import com.devshub.rk.wordsstore.utils.longLiveData


class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val context = app.applicationContext

    val categories: LiveData<PagedList<Category>> by lazy {
        categoryRepository.categoryPagedList(context)
    }

    val categoriesCount: LiveData<Int> by lazy {
        categoryRepository.categoriesCount(context)
    }

    val words: MediatorLiveData<PagedList<WordWithCategory>> = MediatorLiveData()

    val widgetWordLiveData: LiveData<WordWithCategory?> by lazy {
        Transformations.switchMap(context.longLiveData(PREF_WIDGET_WORD_ID, -1)) { widgetWordId ->
            return@switchMap wordRepository.getWordWithCategoryById(context, widgetWordId)
        }
    }

    fun getAllWords() {
        words.addSource(wordRepository.getAllWordsWithCategoryTitlePagedList(context)) {
            words.value = it
        }
    }

    fun filterByCategoryId(categoryId: Long) {
        words.addSource(wordRepository.getAllWordsByCategoryIDPagedList(context, categoryId)) {
            words.value = it
        }
    }

    @IdRes
    var currentTabMenuId: Int = R.id.main_nav_words
}