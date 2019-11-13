package com.devshub.rk.wordsstore.ui.viewholders

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.data.model.Category
import com.devshub.rk.wordsstore.extensions.addSubView
import com.devshub.rk.wordsstore.extensions.setTextAsync
import com.devshub.rk.wordsstore.utils.CategoryItemClickCallback
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_category_chooser.*

/**
 * Created by ZMN on 12/10/18.
 **/
class CategoryChooserHolder private constructor(
    override val containerView: View,
    private val clickCallback: CategoryItemClickCallback
) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    companion object {

        fun create(parent: ViewGroup, clickCallback: CategoryItemClickCallback): CategoryChooserHolder {
            val view = parent.addSubView(R.layout.item_category_chooser)
            return CategoryChooserHolder(view, clickCallback)
        }

    }

    fun bindCategoryFilter(category: Category) {
        itemCategoryChooserTitleTv.setTextAsync(category.title)
        containerView.setOnClickListener { clickCallback(category) }
    }
}