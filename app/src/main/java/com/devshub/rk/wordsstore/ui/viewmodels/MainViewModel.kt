package com.devshub.rk.wordsstore.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.devshub.rk.wordsstore.app.App
import com.devshub.rk.wordsstore.data.model.Category
import com.devshub.rk.wordsstore.data.model.WordWithCategory
import com.devshub.rk.wordsstore.data.repositories.categoryRepository
import com.devshub.rk.wordsstore.data.repositories.wordRepository

/**
 * Created by ZMN on 12/13/18.
 **/
class MainViewModel : ViewModel() {

    val categories: LiveData<PagedList<Category>> by lazy {
        categoryRepository.categoryPagedList(App.instance.applicationContext)
    }

    val words: LiveData<PagedList<WordWithCategory>> by lazy {
        wordRepository.getAllWordsWithCategoryTitlePagedList(App.instance.applicationContext)
    }

}