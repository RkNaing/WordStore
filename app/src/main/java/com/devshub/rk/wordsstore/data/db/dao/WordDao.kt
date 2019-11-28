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
import com.devshub.rk.wordsstore.data.model.Word
import com.devshub.rk.wordsstore.data.model.WordWithCategory

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWord(word: Word): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun updateWord(word: Word): Int

    @Delete
    fun deleteWord(word: Word): Int

    @Query("SELECT COUNT(*) FROM WORD_TBL")
    fun wordsCount(): Int

    @Query("SELECT WORD_TBL.*, CATEGORY_TBL.id AS word_category_id, CATEGORY_TBL.title AS word_category_title, CATEGORY_TBL.description AS word_category_description FROM WORD_TBL INNER JOIN CATEGORY_TBL ON WORD_TBL.categoryId = CATEGORY_TBL.id ORDER BY WORD_TBL.title")
    fun getAllWithCategoryTitle(): DataSource.Factory<Int, WordWithCategory>

    @Query("SELECT WORD_TBL.*, CATEGORY_TBL.id AS word_category_id, CATEGORY_TBL.title AS word_category_title, CATEGORY_TBL.description AS word_category_description FROM WORD_TBL INNER JOIN CATEGORY_TBL ON WORD_TBL.categoryId = CATEGORY_TBL.id WHERE CATEGORY_TBL.id = :categoryID ORDER BY WORD_TBL.title")
    fun getByCategoryId(categoryID: Long): DataSource.Factory<Int, WordWithCategory>

    @Query("SELECT WORD_TBL.*, CATEGORY_TBL.id AS word_category_id, CATEGORY_TBL.title AS word_category_title, CATEGORY_TBL.description AS word_category_description FROM WORD_TBL INNER JOIN CATEGORY_TBL ON WORD_TBL.categoryId = CATEGORY_TBL.id ORDER BY RANDOM() LIMIT 1")
    fun getSingleRandomWordWithCategoryTitle(): WordWithCategory

    @Query("SELECT WORD_TBL.*, CATEGORY_TBL.id AS word_category_id, CATEGORY_TBL.title AS word_category_title, CATEGORY_TBL.description AS word_category_description FROM WORD_TBL INNER JOIN CATEGORY_TBL ON WORD_TBL.categoryId = CATEGORY_TBL.id WHERE WORD_TBL.id = :id")
    fun getById(id: Long): LiveData<WordWithCategory?>

}