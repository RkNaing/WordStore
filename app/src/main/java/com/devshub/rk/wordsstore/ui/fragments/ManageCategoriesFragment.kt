package com.devshub.rk.wordsstore.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.extensions.gone
import com.devshub.rk.wordsstore.extensions.visible
import com.devshub.rk.wordsstore.ui.adapter.CategoriesRVAdapter
import com.devshub.rk.wordsstore.ui.viewmodels.MainViewModel
import com.devshub.rk.wordsstore.utils.SpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_manage_categories.*

/**
 * Created by ZMN on 12/12/18.
 **/
class ManageCategoriesFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_manage_categories

    override fun onViewReady(view: View, savedInstanceState: Bundle?) {

        val adapter = CategoriesRVAdapter(categoryClickCallback = { category ->
            findNavController().navigate(
                ManageCategoriesFragmentDirections.actionSaveCategory()
                    .setArgCategory(category)
            )
        })

        with(ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)) {
            screenTitle.postValue(R.string.lbl_categories)
            fabIcon.postValue(R.drawable.ic_add_white_24dp)
            fabClickListener.postValue(View.OnClickListener {
                findNavController().navigate(R.id.action_saveCategory)
            })
            categories.observe(this@ManageCategoriesFragment, Observer { pagedList ->
                adapter.submitList(pagedList) {
                    if (adapter.itemCount > 0) {
                        manageCategoryEmptyViewContainer.gone()
                        manageCategoriesRV.visible()
                    } else {
                        manageCategoryEmptyViewContainer.visible()
                        manageCategoriesRV.gone()
                    }
                }
            })
            fabVibility.postValue(true)
        }

        manageCategoriesRV.adapter = adapter
        manageCategoriesRV.addItemDecoration(
            SpacingItemDecoration(
                itemSpacingTop = 5,
                itemSpacingLeft = 10,
                itemSpacingBottom = 5,
                itemSpacingRight = 10
            )
        )
    }
}