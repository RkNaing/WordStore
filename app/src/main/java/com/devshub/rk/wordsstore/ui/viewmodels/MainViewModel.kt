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

/**
 * Created by ZMN on 12/13/18.
 **/
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