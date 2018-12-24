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

/**
 * Created by ZMN on 12/12/18.
 **/
class CategoriesListFragment : BaseFragment() {

    private val mainViewModel by lazy { ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java) }

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