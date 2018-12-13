package com.devshub.rk.wordsstore.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.devshub.rk.wordsstore.data.model.Category
import com.devshub.rk.wordsstore.ui.viewholders.CategoryHolder
import com.devshub.rk.wordsstore.utils.CategoryItemClickCallback

/**
 * Created by ZMN on 12/11/18.
 **/
class CategoriesRVAdapter(
    private val editCallback: CategoryItemClickCallback,
    private val deleteCallback: CategoryItemClickCallback
) : PagedListAdapter<Category, CategoryHolder>(Category.diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder.create(parent, editCallback, deleteCallback)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        getItem(position)?.let {
            holder.bindCategory(it)
        }
    }
}