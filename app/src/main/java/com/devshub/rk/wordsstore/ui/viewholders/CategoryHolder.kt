package com.devshub.rk.wordsstore.ui.viewholders

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.data.model.Category
import com.devshub.rk.wordsstore.extensions.addSubView
import com.devshub.rk.wordsstore.utils.CategoryItemClickCallback
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_category.*

/**
 * Created by ZMN on 12/10/18.
 **/
class CategoryHolder private constructor(
    override val containerView: View,
    private val categoryClickCallback: CategoryItemClickCallback
) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    companion object {
        fun create(
            parent: ViewGroup, editCallback: CategoryItemClickCallback
        ): CategoryHolder {
            val view = parent.addSubView(R.layout.item_category)
            return CategoryHolder(
                view,
                categoryClickCallback = editCallback
            )
        }
    }

    fun bindCategory(category: Category) {
        itemCategoryTvTitle.text = category.title
        itemCategoryTvDesc.text = category.description
        itemView.setOnClickListener {
            categoryClickCallback(category)
        }
    }


}