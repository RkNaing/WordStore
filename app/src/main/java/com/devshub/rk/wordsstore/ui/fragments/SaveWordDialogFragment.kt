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
import com.devshub.rk.wordsstore.data.model.Word
import com.devshub.rk.wordsstore.data.repositories.wordRepository
import com.devshub.rk.wordsstore.extensions.*
import com.devshub.rk.wordsstore.utils.CompletionCallback
import kotlinx.android.synthetic.main.fragment_dialog_save_word.*

class SaveWordDialogFragment : DialogFragment() {

    companion object {
        private const val ARG_WORD = "ArgWord"

        fun createInstance(word: Word? = null): SaveWordDialogFragment {
            val fragment = SaveWordDialogFragment()
            val args = Bundle().also { it.putParcelable(ARG_WORD, word) }
            fragment.arguments = args
            return fragment
        }
    }

    private var word: Word? = null
    private var category: Category? = null
    private var isSaveInProgress = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, theme)
        isCancelable = false
        word = arguments?.getParcelable(ARG_WORD)
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
        return inflater.inflate(R.layout.fragment_dialog_save_word, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        word?.let {
            saveWordDialogTvTitle.text = getString(R.string.lbl_title_update_word)
            saveWordDialogEdtTitle.setText(it.title)
            saveWordDialogEdtDesc.setText(it.description)
        }

        saveWordDialogBtnCancel.setOnClickListener {
            activity?.dismissSoftKeyboard()
            dismiss()
        }

        saveWordDialogBtnCategoryChooser.setOnClickListener {
            activity?.let { hostActivity ->
                val categoriesListBottomSheet =
                    CategoryChooserBottomSheetDialogFragment.createInstance(getString(R.string.lbl_choose_category)) { category ->
                        saveWordDialogBtnCategoryChooser.text = category.title
                        this.category = category
                    }
                categoriesListBottomSheet.show(
                    hostActivity.supportFragmentManager,
                    categoriesListBottomSheet.tag
                )
            }
        }

        saveWordDialogBtnSave.setOnClickListener {

            if (isSaveInProgress) return@setOnClickListener

            activity?.dismissSoftKeyboard()

            val enteredTitle = saveWordDialogEdtTitle.trimmedText
            val enteredDesc = saveWordDialogEdtDesc.trimmedText

            if (enteredTitle.isEmpty()) {
                saveWordDialogTilTitle.showError(R.string.validation_enter_word_title)
            } else if (enteredDesc.isEmpty()) {
                saveWordDialogTilTitle.clearError()
                saveWordDialogTilDesc.showError(R.string.validation_enter_word_desc)
            } else {
                saveWordDialogTilTitle.clearError()
                saveWordDialogTilDesc.clearError()
                val chosenCategory = category
                if (chosenCategory == null) {
                    context?.errorToast(R.string.validation_choose_word_category)
                } else {
                    val wordToSave =
                        word?.copy(title = enteredTitle, description = enteredDesc, categoryId = chosenCategory.id)
                            ?: Word(title = enteredTitle, description = enteredDesc, categoryId = chosenCategory.id)
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

                    with(wordRepository) {
                        if (wordToSave.id > 0) {
                            updateWord(requireContext(), wordToSave, completionHandler)
                        } else {
                            addWord(requireContext(), wordToSave, completionHandler)
                        }
                    }
                }
            }
        }
    }
}