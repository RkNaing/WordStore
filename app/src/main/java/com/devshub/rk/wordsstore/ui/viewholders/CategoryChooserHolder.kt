/*
 * Copyright (C) 2019 WordsStore
 *
 * Created by		:	Rahul Kumar
 * Last Modified	:	28 Nov 2019 03:06:10 PM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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

class CategoryChooserHolder private constructor(
    override val containerView: View,
    private val clickCallback: CategoryItemClickCallback
) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    companion object {

        fun create(
            parent: ViewGroup,
            clickCallback: CategoryItemClickCallback
        ): CategoryChooserHolder {
            val view = parent.addSubView(R.layout.item_category_chooser)
            return CategoryChooserHolder(view, clickCallback)
        }

    }

    fun bindCategoryFilter(category: Category) {
        itemCategoryChooserTitleTv.setTextAsync(category.title)
        containerView.setOnClickListener { clickCallback(category) }
    }
}