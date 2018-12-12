package com.devshub.rk.wordsstore.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devshub.rk.wordsstore.data.model.Category
import com.devshub.rk.wordsstore.ui.viewholders.CategoryHolder

/**
 * Created by ZMN on 12/11/18.
 **/
class CategoriesRVAdapter(
    val categories: MutableList<Category> = mutableListOf()
) : RecyclerView.Adapter<CategoryHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder.create(parent)
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bindCategory(categories[position])
    }
}