package com.devshub.rk.wordsstore.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.devshub.rk.wordsstore.data.model.Category
import com.devshub.rk.wordsstore.ui.viewholders.CategoryFilterHolder
import com.devshub.rk.wordsstore.utils.CategoryItemClickCallback

/**
 * Created by ZMN on 12/10/18.
 **/
class CategoriesFilterRVAdapter(private val clickCallback: CategoryItemClickCallback) :
    PagedListAdapter<Category, CategoryFilterHolder>(Category.diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryFilterHolder {
        return CategoryFilterHolder.create(parent, clickCallback)
    }

    override fun onBindViewHolder(filterHolder: CategoryFilterHolder, position: Int) {
        getItem(position)?.let { filterHolder.bindCategoryFilter(it) }
    }
}