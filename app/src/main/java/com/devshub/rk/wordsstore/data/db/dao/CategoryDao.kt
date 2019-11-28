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

package com.devshub.rk.wordsstore.data.db.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.devshub.rk.wordsstore.data.model.Category

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createCategory(category: Category): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createCategories(categories: List<Category>): List<Long>

    @Query("SELECT * FROM CATEGORY_TBL ORDER BY CATEGORY_TBL.title")
    fun getAll(): DataSource.Factory<Int, Category>

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun updateCategory(category: Category): Int

    @Delete
    fun deleteCategory(category: Category): Int

    @Query("SELECT COUNT(*) FROM CATEGORY_TBL")
    fun categoriesCount(): LiveData<Int>
}