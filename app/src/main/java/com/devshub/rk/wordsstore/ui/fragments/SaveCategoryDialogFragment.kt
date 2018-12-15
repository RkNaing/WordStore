package com.devshub.rk.wordsstore.ui.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.data.model.Category
import com.devshub.rk.wordsstore.data.repositories.categoryRepository
import com.devshub.rk.wordsstore.extensions.*
import kotlinx.android.synthetic.main.fragment_dialog_save_category.*

/**
 * Created by ZMN on 12/11/18.
 **/
class SaveCategoryDialogFragment : DialogFragment() {

    companion object {
        private const val ARG_CATEGORY = "ArgCategory"

        fun createInstance(category: Category? = null): SaveCategoryDialogFragment {
            val fragment = SaveCategoryDialogFragment()
            fragment.arguments = Bundle().also { it.putParcelable(ARG_CATEGORY, category) }
            return fragment
        }
    }

    private var category: Category? = null
    private var isSaveInProgress = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, theme)
        isCancelable = false
        category = arguments?.getParcelable(ARG_CATEGORY)
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.fragment_dialog_save_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        category?.let {
            saveCategoryDialogTvTitle.text = getString(R.string.lbl_title_update_category)
            saveCategoryDialogEdtTitle.setText(it.title)
            saveCategoryDialogEdtDesc.setText(it.description)
        }

        saveCategoryDialogBtnCancel.setOnClickListener {
            activity?.dismissSoftKeyboard()
            dismiss()
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

                val completionHandler: (Boolean) -> Unit = { isSuccess ->
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