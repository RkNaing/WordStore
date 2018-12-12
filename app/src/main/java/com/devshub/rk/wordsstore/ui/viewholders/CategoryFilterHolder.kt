package com.devshub.rk.wordsstore.ui.viewholders

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.data.model.Category
import com.devshub.rk.wordsstore.extensions.addSubView

/**
 * Created by ZMN on 12/10/18.
 **/
class CategoryFilterHolder private constructor(view: View) : RecyclerView.ViewHolder(view) {

    companion object {

        fun create(parent: ViewGroup) =
            CategoryFilterHolder(parent.addSubView(R.layout.item_category_filter))
    }

    private val tvCategoryTitle: AppCompatTextView by lazy {
        itemView.findViewById<AppCompatTextView>(
            R.id.itemCategoryFilterTv
        )
    }

    fun bindCategoryFilter(category: Category) {
        tvCategoryTitle.text = category.title
    }
}