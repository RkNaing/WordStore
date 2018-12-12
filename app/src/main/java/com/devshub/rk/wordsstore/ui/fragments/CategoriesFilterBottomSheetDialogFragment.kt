package com.devshub.rk.wordsstore.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.ui.adapter.CategoriesFilterRVAdapter
import com.devshub.rk.wordsstore.utils.getDummyCategories
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_categories_filter_bottom_drawer.*

/**
 * Created by ZMN on 12/10/18.
 **/
class CategoriesFilterBottomSheetDialogFragment : BottomSheetDialogFragment() {

    override fun getTheme() = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?) =
        BottomSheetDialog(requireContext(), theme)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories_filter_bottom_drawer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoriesFiltersRv.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        categoriesFiltersRv.setHasFixedSize(true)
        categoriesFiltersRv.adapter = CategoriesFilterRVAdapter(getDummyCategories())

    }

}