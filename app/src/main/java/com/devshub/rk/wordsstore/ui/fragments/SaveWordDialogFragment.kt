package com.devshub.rk.wordsstore.ui.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.data.model.Word
import com.devshub.rk.wordsstore.extensions.dismissSoftKeyboard
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
                    }
                categoriesListBottomSheet.show(
                    hostActivity.supportFragmentManager,
                    categoriesListBottomSheet.tag
                )
            }
        }
    }
}