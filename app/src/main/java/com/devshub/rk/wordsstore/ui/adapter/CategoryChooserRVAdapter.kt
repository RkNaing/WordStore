/*
 * Copyright (C) 2019 WordsStore
 *
 * Created by		:	Rahul Kumar
 * Last Modified	:	28 Nov 2019 03:06:11 PM
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

package com.devshub.rk.wordsstore.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.devshub.rk.wordsstore.data.model.Category
import com.devshub.rk.wordsstore.ui.viewholders.CategoryChooserHolder
import com.devshub.rk.wordsstore.utils.CategoryItemClickCallback


class CategoryChooserRVAdapter(private val clickCallback: CategoryItemClickCallback) :
    PagedListAdapter<Category, CategoryChooserHolder>(Category.diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryChooserHolder {
        return CategoryChooserHolder.create(parent, clickCallback)
    }

    override fun onBindViewHolder(chooserHolder: CategoryChooserHolder, position: Int) {
        getItem(position)?.let { chooserHolder.bindCategoryFilter(it) }
    }
}