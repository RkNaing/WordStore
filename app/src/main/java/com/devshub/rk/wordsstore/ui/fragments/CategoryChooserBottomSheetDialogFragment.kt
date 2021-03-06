/*
 * Copyright (C) 2019 WordsStore
 *
 * Created by		:	Rahul Kumar
 * Last Modified	:	28 Nov 2019 03:06:10 PM
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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.ui.adapter.CategoryChooserRVAdapter
import com.devshub.rk.wordsstore.ui.viewmodels.MainViewModel
import com.devshub.rk.wordsstore.utils.CategoryItemClickCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_category_chooser_bottom_drawer.*


class CategoryChooserBottomSheetDialogFragment : BottomSheetDialogFragment() {


    companion object {

        private const val ARG_CHOOSER_TITLE = "ArgChooserTitle"

        fun createInstance(
            chooserTitle: String,
            chooserCallback: CategoryItemClickCallback
        ): CategoryChooserBottomSheetDialogFragment {
            val fragment = CategoryChooserBottomSheetDialogFragment()
            val args = Bundle().also { it.putString(ARG_CHOOSER_TITLE, chooserTitle) }
            fragment.arguments = args
            fragment.chooserCallback = chooserCallback
            return fragment
        }
    }

    lateinit var chooserCallback: CategoryItemClickCallback

    override fun getTheme() = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?) =
        BottomSheetDialog(requireContext(), theme)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category_chooser_bottom_drawer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryChooserTitleLbl.text =
            arguments?.getString(ARG_CHOOSER_TITLE) ?: getString(R.string.lbl_categories)

        categoryChooserRv.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        val adapter = CategoryChooserRVAdapter {
            chooserCallback(it)
            this.dismiss()
        }

        categoryChooserRv.adapter = adapter

        val mainViewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.categories.observe(this, Observer {
            adapter.submitList(it) {
                if (adapter.itemCount == 0) {
                    dismiss()
                }
            }
        })

    }

}