package com.devshub.rk.wordsstore.data.db.dao

import androidx.paging.DataSource
import androidx.room.*
import com.devshub.rk.wordsstore.data.model.Word
import com.devshub.rk.wordsstore.data.model.WordWithCategory

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWord(word: Word): Long

    @Update(onConflict = OnConflictStrategy.ROLLBACK)
    fun updateWord(word: Word): Int

    @Delete
    fun deleteWord(word: Word): Int

    @Query("SELECT WORD_TBL.*, CATEGORY_TBL.title AS category_title FROM WORD_TBL INNER JOIN CATEGORY_TBL ON WORD_TBL.categoryId = CATEGORY_TBL.id")
    fun getAllWithCategoryTitle():DataSource.Factory<Int, WordWithCategory>
}