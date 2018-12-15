package com.devshub.rk.wordsstore.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.devshub.rk.wordsstore.app.App
import com.devshub.rk.wordsstore.data.model.Category
import com.devshub.rk.wordsstore.data.model.Word
import com.devshub.rk.wordsstore.data.repositories.categoryRepository

/**
 * Created by ZMN on 12/13/18.
 **/
class MainViewModel : ViewModel() {

    val categories: LiveData<PagedList<Category>> by lazy {
        categoryRepository.categoryPagedList(App.instance.applicationContext)
    }

    val words: MutableLiveData<PagedList<Word>> = MutableLiveData()

}