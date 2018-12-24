package com.devshub.rk.wordsstore.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.devshub.rk.wordsstore.data.db.dao.CategoryDao
import com.devshub.rk.wordsstore.data.db.dao.WordDao
import com.devshub.rk.wordsstore.data.model.Category
import com.devshub.rk.wordsstore.data.model.Word
import com.devshub.rk.wordsstore.data.repositories.categoryRepository
import com.devshub.rk.wordsstore.utils.SingletonHolder
import timber.log.Timber

/**
 * Created by ZMN on 12/13/18.
 **/
@Database(
    entities = [Category::class, Word::class],
    version = 1,
    exportSchema = false
)
abstract class AppDB : RoomDatabase() {

    companion object : SingletonHolder<AppDB, Context>(creator = { context ->
        Room.databaseBuilder(
            context.applicationContext,
            AppDB::class.java,
            "WordStore.db"
        ).addCallback(object : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Timber.d("Populating Stocked Categories")
                categoryRepository.createCategories(context, Category.getStockedCategories()) { success ->
                    Timber.d("Finished populating stocked categories. Is Success $success")
                }
            }

        }).build()
    })

    abstract fun getCategoryDao(): CategoryDao

    abstract fun getWordDao(): WordDao

}