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

/**
 * Created by ZMN on 12/13/18.
 **/
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