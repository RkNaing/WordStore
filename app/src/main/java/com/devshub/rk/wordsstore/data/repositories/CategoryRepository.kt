package com.devshub.rk.wordsstore.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.devshub.rk.wordsstore.data.model.Category
import com.devshub.rk.wordsstore.utils.CompletionCallback

/**
 * Created by ZMN on 12/13/18.
 **/
interface CategoryRepository {

    fun createCategory(context: Context, category: Category, completion: CompletionCallback)

    fun updateCategory(context: Context, category: Category, completion: CompletionCallback)

    fun deleteCategory(context: Context, category: Category, completion: CompletionCallback)

    fun categoryPagedList(context: Context): LiveData<PagedList<Category>>

    fun categoriesCount(context: Context): LiveData<Int>
}