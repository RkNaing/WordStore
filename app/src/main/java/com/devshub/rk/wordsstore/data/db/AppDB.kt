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
                categoryRepository.createCategories(
                    context,
                    Category.getStockedCategories()
                ) { success ->
                    Timber.d("Finished populating stocked categories. Is Success $success")
                }
            }

        }).build()
    })

    abstract fun getCategoryDao(): CategoryDao

    abstract fun getWordDao(): WordDao

}