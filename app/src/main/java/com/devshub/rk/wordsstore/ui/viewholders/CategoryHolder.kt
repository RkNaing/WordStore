package com.devshub.rk.wordsstore.ui.viewholders

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.data.model.Category
import com.devshub.rk.wordsstore.extensions.addSubView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_category.*

/**
 * Created by ZMN on 12/10/18.
 **/
class CategoryHolder private constructor(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    companion object {
        fun create(parent: ViewGroup) = CategoryHolder(parent.addSubView(R.layout.item_category))
    }

    fun bindCategory(category: Category) {
        itemCategoryTvTitle.text = category.title
        itemCategoryTvDesc.text = category.description
        itemCategoryControlEdit.setOnClickListener {
            Toast.makeText(containerView.context, "Edit ${category.title}", Toast.LENGTH_SHORT)
                .show()
        }
        itemCategoryControlDelete.setOnClickListener {
            Toast.makeText(containerView.context, "Delete ${category.title}", Toast.LENGTH_SHORT)
                .show()
        }
    }


}