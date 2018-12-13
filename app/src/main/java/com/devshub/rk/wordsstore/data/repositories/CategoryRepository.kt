package com.devshub.rk.wordsstore.data.repositories

import android.content.Context
import com.devshub.rk.wordsstore.data.model.Category
import com.devshub.rk.wordsstore.utils.CompletionCallback

/**
 * Created by ZMN on 12/13/18.
 **/
interface CategoryRepository {

    fun createCategory(context: Context, category: Category, completion: CompletionCallback)

    fun updateCategory(context: Context, category: Category, completion: CompletionCallback)

    fun deleteCategory(context: Context, category: Category, completion: CompletionCallback)
}