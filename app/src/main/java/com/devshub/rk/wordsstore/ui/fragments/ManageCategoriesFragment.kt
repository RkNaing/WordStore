package com.devshub.rk.wordsstore.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.data.repositories.getCategoryRepository
import com.devshub.rk.wordsstore.extensions.errorToast
import com.devshub.rk.wordsstore.extensions.getHtml
import com.devshub.rk.wordsstore.extensions.showDeleteConfirmDialog
import com.devshub.rk.wordsstore.extensions.successToast
import com.devshub.rk.wordsstore.ui.adapter.CategoriesRVAdapter
import com.devshub.rk.wordsstore.ui.viewmodels.MainViewModel
import com.devshub.rk.wordsstore.utils.SpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_manage_categories.*

/**
 * Created by ZMN on 12/12/18.
 **/
class ManageCategoriesFragment : BaseFragment() {

    companion object {
        const val TAG = "ManageCategoriesFragment"
    }


    override fun getLayoutId() = R.layout.fragment_manage_categories

    override fun onViewReady(view: View, savedInstanceState: Bundle?) {
        val adapter = CategoriesRVAdapter(editCallback = { category ->
            activity?.let {
                val saveCategoryDialogFragment = SaveCategoryDialogFragment.createInstance(category)
                saveCategoryDialogFragment.show(
                    it.supportFragmentManager,
                    saveCategoryDialogFragment.tag
                )
            }
        }, deleteCallback = { category ->
            val deleteConfirmMessage =
                getString(R.string.msg_confirm_delete_category, category.title).getHtml()
            activity?.let {
                it.showDeleteConfirmDialog(deleteConfirmMessage) {
                    getCategoryRepository().deleteCategory(it, category) { isSuccess ->
                        if (isSuccess) {
                            it.successToast(R.string.msg_info_delete_success)
                        } else {
                            it.errorToast(R.string.msg_err_delete_failed)
                        }
                    }
                }
            }
        })

        manageCategoriesRV.adapter = adapter
        manageCategoriesRV.addItemDecoration(
            SpacingItemDecoration(
                itemSpacingTop = 5,
                itemSpacingLeft = 10,
                itemSpacingBottom = 5,
                itemSpacingRight = 10,
                topMostSpacing = 10,
                bottomMostSpacing = 90
            )
        )

        val mainViewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.categories.observe(this, Observer { pagedList ->
            adapter.submitList(pagedList)
        })
    }
}