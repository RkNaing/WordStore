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
import com.devshub.rk.wordsstore.data.model.Word
import com.devshub.rk.wordsstore.data.repositories.wordRepository
import com.devshub.rk.wordsstore.extensions.*
import com.devshub.rk.wordsstore.ui.viewmodels.MainViewModel
import com.devshub.rk.wordsstore.utils.CompletionCallback
import kotlinx.android.synthetic.main.fragment_save_word.*

/**
 * Created by ZMN on 12/20/18.
 **/
class SaveWordFragment : BaseFragment() {

    private var word: Word? = null
    private var category: Category? = null
    private var isSaveInProgress = false

    override fun getLayoutId() = R.layout.fragment_save_word

    override fun onViewReady(view: View, savedInstanceState: Bundle?) {

        arguments?.let {
            word = SaveWordFragmentArgs.fromBundle(it).argWordWithCategory?.word
            category = SaveWordFragmentArgs.fromBundle(it).argWordWithCategory?.wordCategory
        }

        @StringRes var titleStringRes: Int = R.string.lbl_title_create_word

        word?.let {
            titleStringRes = R.string.lbl_title_update_word
            saveWordEdtTitle.setText(it.title)
            saveWordEdtDesc.setText(it.description)
            setHasOptionsMenu(true)
        }

        category?.let {
            saveWordBtnCategoryChooser.text = it.title
        }

        with(ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)) {
            screenTitle.postValue(titleStringRes)
            fabIcon.postValue(R.drawable.ic_save_white_24dp)
            fabClickListener.postValue(View.OnClickListener {
                if (isSaveInProgress) return@OnClickListener

                activity?.dismissSoftKeyboard()

                val enteredTitle = saveWordEdtTitle.trimmedText
                val enteredDesc = saveWordEdtDesc.trimmedText

                if (enteredTitle.isEmpty()) {
                    saveWordTilTitle.showError(R.string.validation_enter_word_title)
                } else if (enteredDesc.isEmpty()) {
                    saveWordTilTitle.clearError()
                    saveWordTilDesc.showError(R.string.validation_enter_word_desc)
                } else {
                    saveWordTilTitle.clearError()
                    saveWordTilDesc.clearError()
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
                                findNavController().navigateUp()
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


            })
        }

        saveWordBtnCategoryChooser.setOnClickListener {
            activity?.let { hostActivity ->
                val categoriesListBottomSheet =
                    CategoryChooserBottomSheetDialogFragment.createInstance(getString(R.string.lbl_choose_category)) { category ->
                        saveWordBtnCategoryChooser.text = category.title
                        this.category = category
                    }
                categoriesListBottomSheet.show(
                    hostActivity.supportFragmentManager,
                    categoriesListBottomSheet.tag
                )
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        word?.let {
            inflater?.inflate(R.menu.delete_menu, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_delete -> {
                word?.let { unwrappedWord ->
                    val deleteConfirmMessage =
                        getString(R.string.msg_confirm_delete_word, unwrappedWord.title).getHtml()
                    activity?.let { unwrappedActivity ->
                        unwrappedActivity.showDeleteConfirmDialog(deleteConfirmMessage) {
                            wordRepository.deleteWord(unwrappedActivity, unwrappedWord) { isSuccess ->
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