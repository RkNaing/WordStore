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
import androidx.fragment.app.FragmentManager
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.data.model.Category
import com.devshub.rk.wordsstore.data.model.Word
import com.devshub.rk.wordsstore.data.model.WordWithCategory
import com.devshub.rk.wordsstore.data.repositories.wordRepository
import com.devshub.rk.wordsstore.extensions.*
import com.devshub.rk.wordsstore.utils.CompletionCallback
import kotlinx.android.synthetic.main.fragment_dialog_save_word.*

class SaveWordDialogFragment : BaseDialogFragment() {

    companion object {
        private const val ARG_WORD = "ArgWord"

        private fun createInstance(word: WordWithCategory? = null): SaveWordDialogFragment {
            val fragment = SaveWordDialogFragment()
            val args = Bundle().also { it.putParcelable(ARG_WORD, word) }
            fragment.arguments = args
            return fragment
        }

        fun show(supportFragmentManager: FragmentManager, word: WordWithCategory? = null) {
            val saveWordDialog = createInstance(word)
            saveWordDialog.show(supportFragmentManager, saveWordDialog::class.java.simpleName)
        }
    }

    private var word: Word? = null
    private var category: Category? = null
    private var isSaveInProgress = false

    override fun getLayoutResource(): Int = R.layout.fragment_dialog_save_word

    override fun onViewReady(view: View, savedInstanceState: Bundle?) {
        val wordWithCategory: WordWithCategory? = arguments?.getParcelable(ARG_WORD)
        wordWithCategory?.let {
            this.word = it.word
            this.category = it.wordCategory
        }

        word?.let {
            saveWordDialogTvTitle.text = getString(R.string.lbl_title_update_word)
            saveWordDialogEdtTitle.setText(it.title)
            saveWordDialogEdtDesc.setText(it.description)
            saveWordDialogBtnDelete.visibility = View.VISIBLE
        }

        category?.let {
            saveWordDialogBtnCategoryChooser.text = it.title
        }

        saveWordDialogBtnCancel.setOnClickListener {
            activity?.dismissSoftKeyboard()
            dismiss()
        }

        saveWordDialogBtnDelete.setOnClickListener {
            word?.let { unwrappedWord ->
                activity?.dismissSoftKeyboard()
                val deleteConfirmMessage =
                    getString(R.string.msg_confirm_delete_word, unwrappedWord.title).getHtml()
                activity?.let { unwrappedActivity ->
                    unwrappedActivity.showDeleteConfirmDialog(deleteConfirmMessage) {
                        wordRepository.deleteWord(unwrappedActivity, unwrappedWord) { isSuccess ->
                            if (isSuccess) {
                                unwrappedActivity.successToast(R.string.msg_info_delete_success)
                                activity?.dismissSoftKeyboard()
                                dismiss()
                            } else {
                                unwrappedActivity.errorToast(R.string.msg_err_delete_failed)
                            }
                        }
                    }
                }
            }
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
                        word?.copy(
                            title = enteredTitle,
                            description = enteredDesc,
                            categoryId = chosenCategory.id
                        )
                            ?: Word(
                                title = enteredTitle,
                                description = enteredDesc,
                                categoryId = chosenCategory.id
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