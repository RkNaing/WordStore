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

package com.devshub.rk.wordsstore.data.repositories.impl

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.devshub.rk.wordsstore.data.db.AppDB
import com.devshub.rk.wordsstore.data.db.dao.CategoryDao
import com.devshub.rk.wordsstore.data.model.Category
import com.devshub.rk.wordsstore.data.repositories.CategoryRepository
import com.devshub.rk.wordsstore.utils.CompletionCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryRepositoryImpl : CategoryRepository {

    override fun createCategory(
        context: Context,
        category: Category,
        completion: CompletionCallback
    ) {

        operateInIO(context, { createdRowId ->
            completion(createdRowId > 0)
        }, { createCategory(category) })
    }

    override fun createCategories(
        context: Context,
        categories: List<Category>,
        completion: CompletionCallback
    ) {
        operateInIO(context, {
            completion(it.isNotEmpty())
        }, { createCategories(categories) })
    }

    override fun updateCategory(
        context: Context,
        category: Category,
        completion: CompletionCallback
    ) {
        operateInIO(context, { numOfRowsAffected ->
            completion(numOfRowsAffected > 0)
        }, { updateCategory(category) })
    }

    override fun deleteCategory(
        context: Context,
        category: Category,
        completion: CompletionCallback
    ) {
        operateInIO(context, { numOfRowsAffected ->
            completion(numOfRowsAffected > 0)
        }, { deleteCategory(category) })
    }

    override fun categoryPagedList(context: Context): LiveData<PagedList<Category>> {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPrefetchDistance(10)
            .setPageSize(20)
            .build()

        return LivePagedListBuilder(
            AppDB.getInstance(context).getCategoryDao().getAll(),
            pagedListConfig
        ).build()
    }

    override fun categoriesCount(context: Context): LiveData<Int> {
        return AppDB.getInstance(context).getCategoryDao().categoriesCount()
    }

    private inline fun <T> operateInIO(
        context: Context,
        crossinline resultHandler: (T) -> Unit,
        crossinline ioJob: CategoryDao.() -> T
    ) {

        GlobalScope.launch(Dispatchers.IO) {

            val result = AppDB.getInstance(context).getCategoryDao().ioJob()

            withContext(Dispatchers.Main) {
                resultHandler(result)
            }

        }

    }
}