package com.devshub.rk.wordsstore.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.data.model.Category
import com.devshub.rk.wordsstore.data.repositories.categoryRepository
import com.devshub.rk.wordsstore.extensions.*
import com.devshub.rk.wordsstore.ui.viewmodels.MainViewModel
import com.devshub.rk.wordsstore.utils.CompletionCallback
import kotlinx.android.synthetic.main.fragment_save_category.*

/**
 * Created by ZMN on 12/20/18.
 **/
class SaveCategoryFragment : BaseFragment() {

    private var category: Category? = null
    private var isSaveInProgress = false

    override fun getLayoutId() = R.layout.fragment_save_category

    override fun onViewReady(view: View, savedInstanceState: Bundle?) {

        arguments?.let {
            category = SaveCategoryFragmentArgs.fromBundle(it).argCategory
        }

        @StringRes var titleStringRes: Int = R.string.lbl_title_create_category

        category?.let {
            saveCategoryEdtTitle.setText(it.title)
            saveCategoryEdtDesc.setText(it.description)
            titleStringRes = R.string.lbl_title_update_category
            setHasOptionsMenu(true)
        }

        with(ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)) {
            screenTitle.postValue(titleStringRes)
            fabIcon.postValue(R.drawable.ic_save_white_24dp)
            fabClickListener.postValue(View.OnClickListener {
                if (isSaveInProgress) return@OnClickListener

                activity?.dismissSoftKeyboard()

                val enteredTitle = saveCategoryEdtTitle.trimmedText
                val enteredDesc = saveCategoryEdtDesc.trimmedText

                if (enteredTitle.isEmpty()) {
                    saveCategoryTilTitle.showError(R.string.validation_enter_category_title)
                } else {
                    saveCategoryTilTitle.clearError()

                    val categoryToSave =
                        category?.copy(title = enteredTitle, description = enteredDesc) ?: Category(
                            title = enteredTitle,
                            description = enteredDesc
                        )

                    isSaveInProgress = true

                    val completionHandler: CompletionCallback = { isSuccess ->
                        isSaveInProgress = false

                        if (isSuccess) {
                            requireContext().successToast(R.string.msg_info_save_success)
                            findNavController().navigateUp()
                        } else {
                            requireContext().errorToast(R.string.msg_err_save_failed)
                        }
                    }

                    with(categoryRepository) {

                        if (categoryToSave.id > 0) {
                            updateCategory(requireContext(), categoryToSave, completionHandler)
                        } else {
                            createCategory(requireContext(), categoryToSave, completionHandler)
                        }
                    }

                }
            })
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        category?.let {
            inflater?.inflate(R.menu.delete_menu, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.menu_delete -> {
                category?.let { unwrappedCategory ->
                    val deleteConfirmMessage =
                        getString(R.string.msg_confirm_delete_category, unwrappedCategory.title).getHtml()
                    activity?.let { unwrappedActivity ->
                        unwrappedActivity.showDeleteConfirmDialog(deleteConfirmMessage) {
                            categoryRepository.deleteCategory(unwrappedActivity, unwrappedCategory) { isSuccess ->
                                if (isSuccess) {
                                    unwrappedActivity.successToast(R.string.msg_info_delete_success)
                                    findNavController().navigateUp()
                                } else {
                                    unwrappedActivity.errorToast(R.string.msg_err_delete_failed)
                                }
                            }
                        }
                    }
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}