package com.devshub.rk.wordsstore.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.devshub.rk.wordsstore.app.App
import com.devshub.rk.wordsstore.data.db.AppDB
import com.devshub.rk.wordsstore.data.model.Category

/**
 * Created by ZMN on 12/13/18.
 **/
class MainViewModel : ViewModel() {

    val categories: LiveData<PagedList<Category>> by lazy {

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPrefetchDistance(10)
            .setPageSize(20)
            .build()

        LivePagedListBuilder(
            AppDB.getInstance(App.instance).getCategoryDao().getAll(),
            pagedListConfig
        ).build()

    }


}