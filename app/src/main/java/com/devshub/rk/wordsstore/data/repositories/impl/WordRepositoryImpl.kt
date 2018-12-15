package com.devshub.rk.wordsstore.data.repositories.impl

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.devshub.rk.wordsstore.data.db.AppDB
import com.devshub.rk.wordsstore.data.db.dao.WordDao
import com.devshub.rk.wordsstore.data.model.Word
import com.devshub.rk.wordsstore.data.model.WordWithCategory
import com.devshub.rk.wordsstore.data.repositories.WordRepository
import com.devshub.rk.wordsstore.utils.CompletionCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WordRepositoryImpl : WordRepository {

    override fun addWord(context: Context, word: Word, completion: CompletionCallback) {
        operateInIO(context, { createdRowId -> completion(createdRowId > 0) }, { addWord(word) })
    }

    override fun updateWord(context: Context, word: Word, completion: CompletionCallback) {
        operateInIO(context, { numOfRowsAffected -> completion(numOfRowsAffected > 0) }, { updateWord(word) })
    }

    override fun deleteWord(context: Context, word: Word, completion: CompletionCallback) {
        operateInIO(context, { numOfRowsAffected -> completion(numOfRowsAffected > 0) }, { deleteWord(word) })
    }

    override fun getAllWordsWithCategoryTitlePagedList(context: Context): LiveData<PagedList<WordWithCategory>> {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPrefetchDistance(10)
            .setPageSize(20)
            .build()

        return LivePagedListBuilder(
            AppDB.getInstance(context).getWordDao().getAllWithCategoryTitle(),
            pagedListConfig
        ).build()
    }

    private inline fun <T> operateInIO(
        context: Context,
        crossinline resultHandler: (T) -> Unit,
        crossinline ioJob: WordDao.() -> T
    ) {

        GlobalScope.launch(Dispatchers.IO) {

            val result = AppDB.getInstance(context).getWordDao().ioJob()

            withContext(Dispatchers.Main) {
                resultHandler(result)
            }

        }

    }

}