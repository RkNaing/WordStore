package com.devshub.rk.wordsstore.data.db.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.devshub.rk.wordsstore.data.model.Category

/**
 * Created by ZMN on 12/13/18.
 **/
@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createCategory(category: Category): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createCategories(categories: List<Category>): List<Long>

    @Query("SELECT * FROM CATEGORY_TBL")
    fun getAll(): DataSource.Factory<Int, Category>

    @Update(onConflict = OnConflictStrategy.ROLLBACK)
    fun updateCategory(category: Category): Int

    @Delete
    fun deleteCategory(category: Category): Int

    @Query("SELECT COUNT(*) FROM CATEGORY_TBL")
    fun categoriesCount(): LiveData<Int>
}