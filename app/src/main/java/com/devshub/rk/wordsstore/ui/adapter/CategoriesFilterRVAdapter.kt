package com.devshub.rk.wordsstore.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devshub.rk.wordsstore.data.model.Category
import com.devshub.rk.wordsstore.ui.viewholders.CategoryFilterHolder

/**
 * Created by ZMN on 12/10/18.
 **/
class CategoriesFilterRVAdapter(var categories: MutableList<Category> = mutableListOf()) :
    RecyclerView.Adapter<CategoryFilterHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryFilterHolder {
        return CategoryFilterHolder.create(parent)
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(filterHolder: CategoryFilterHolder, position: Int) {
        filterHolder.bindCategoryFilter(categories[position])
    }
}