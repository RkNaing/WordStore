package com.devshub.rk.wordsstore.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.data.model.Category
import com.devshub.rk.wordsstore.data.repositories.categoryRepository
import com.devshub.rk.wordsstore.extensions.*
import com.devshub.rk.wordsstore.utils.CompletionCallback
import kotlinx.android.synthetic.main.fragment_dialog_save_category.*

/**
 * Created by ZMN on 12/11/18.
 **/
class SaveCategoryDialogFragment : BaseDialogFragment() {

    companion object {
        private const val ARG_CATEGORY = "ArgCategory"

        private fun createInstance(category: Category? = null): SaveCategoryDialogFragment {
            val fragment = SaveCategoryDialogFragment()
            val args = Bundle().also { it.putParcelable(ARG_CATEGORY, category) }
            fragment.arguments = args
            return fragment
        }

        fun show(supportFragmentManager: FragmentManager, category: Category? = null) {
            val saveCategoryDialog = createInstance(category)
            saveCategoryDialog.show(
                supportFragmentManager,
                saveCategoryDialog::class.java.simpleName
            )
        }

    }

    private var category: Category? = null
    private var isSaveInProgress = false

    override fun getLayoutResource(): Int = R.layout.fragment_dialog_save_category

    override fun onViewReady(view: View, savedInstanceState: Bundle?) {
        category = arguments?.getParcelable(ARG_CATEGORY)

        category?.let {
            saveCategoryDialogTvTitle.text = getString(R.string.lbl_title_update_category)
            saveCategoryDialogEdtTitle.setText(it.title)
            saveCategoryDialogEdtDesc.setText(it.description)
            saveCategoryDialogBtnDelete.visibility = View.VISIBLE
        }

        saveCategoryDialogBtnCancel.setOnClickListener {
            activity?.dismissSoftKeyboard()
            dismiss()
        }

        saveCategoryDialogBtnDelete.setOnClickListener {
            category?.let { unwrappedCategory ->
                activity?.dismissSoftKeyboard()
                val deleteConfirmMessage =
                    getString(
                        R.string.msg_confirm_delete_category,
                        unwrappedCategory.title
                    ).getHtml()
                activity?.let { unwrappedActivity ->
                    unwrappedActivity.showDeleteConfirmDialog(deleteConfirmMessage) {
                        categoryRepository.deleteCategory(
                            unwrappedActivity,
                            unwrappedCategory
                        ) { isSuccess ->
                            if (isSuccess) {
                                unwrappedActivity.successToast(R.string.msg_info_delete_success)
                                dismiss()
                            } else {
                                unwrappedActivity.errorToast(R.string.msg_err_delete_failed)
                            }
                        }
                    }
                }
            }
        }

        saveCategoryDialogBtnSave.setOnClickListener {

            if (isSaveInProgress) return@setOnClickListener

            activity?.dismissSoftKeyboard()

            val enteredTitle = saveCategoryDialogEdtTitle.trimmedText
            val enteredDesc = saveCategoryDialogEdtDesc.trimmedText

            if (enteredTitle.isEmpty()) {
                saveCategoryDialogTilTitle.showError(R.string.validation_enter_category_title)
            } else {
                saveCategoryDialogTilTitle.clearError()

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
                        dismiss()
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
        }

    }
}