package com.devshub.rk.wordsstore.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.devshub.rk.wordsstore.data.model.Category
import com.devshub.rk.wordsstore.ui.viewholders.CategoryChooserHolder
import com.devshub.rk.wordsstore.utils.CategoryItemClickCallback

/**
 * Created by ZMN on 12/10/18.
 **/
class CategoryChooserRVAdapter(private val clickCallback: CategoryItemClickCallback) :
    PagedListAdapter<Category, CategoryChooserHolder>(Category.diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryChooserHolder {
        return CategoryChooserHolder.create(parent, clickCallback)
    }

    override fun onBindViewHolder(chooserHolder: CategoryChooserHolder, position: Int) {
        getItem(position)?.let { chooserHolder.bindCategoryFilter(it) }
    }
}