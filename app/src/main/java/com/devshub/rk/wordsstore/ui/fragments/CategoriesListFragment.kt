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

package com.devshub.rk.wordsstore.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.extensions.gone
import com.devshub.rk.wordsstore.extensions.visible
import com.devshub.rk.wordsstore.ui.adapter.CategoriesRVAdapter
import com.devshub.rk.wordsstore.ui.viewmodels.MainViewModel
import com.devshub.rk.wordsstore.utils.SpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_categories.*


class CategoriesListFragment : BaseFragment() {

    private val mainViewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
    }

    override fun getLayoutId() = R.layout.fragment_categories

    override fun onViewReady(view: View, savedInstanceState: Bundle?) {

        val adapter = CategoriesRVAdapter(categoryClickCallback = { category ->
            SaveCategoryDialogFragment.show(requireFragmentManager(), category)
        })

        with(mainViewModel) {
            categories.observe(this@CategoriesListFragment, Observer { pagedList ->
                adapter.submitList(pagedList) {
                    if (adapter.itemCount > 0) {
                        categoryEmptyViewContainer.gone()
                        categoriesRV.visible()
                    } else {
                        categoryEmptyViewContainer.visible()
                        categoriesRV.gone()
                    }
                }
            })
        }

        categoriesRV.adapter = adapter
        categoriesRV.addItemDecoration(
            SpacingItemDecoration(
                itemSpacingTop = 5,
                itemSpacingLeft = 10,
                itemSpacingBottom = 5,
                itemSpacingRight = 10
            )
        )
    }

}