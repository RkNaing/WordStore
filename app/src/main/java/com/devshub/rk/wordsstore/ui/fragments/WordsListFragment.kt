package com.devshub.rk.wordsstore.ui.fragments

import android.os.Bundle
import android.view.View
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.ui.adapter.WordsRVAdapter
import com.devshub.rk.wordsstore.utils.SpacingItemDecoration
import com.devshub.rk.wordsstore.utils.getDummyWords
import kotlinx.android.synthetic.main.fragment_words.*

/**
 * Created by ZMN on 12/12/18.
 **/
class WordsListFragment : BaseFragment() {

    companion object {
        const val TAG = "WordsListFragment"
    }

    override fun getLayoutId() = R.layout.fragment_words

    override fun onViewReady(view: View, savedInstanceState: Bundle?) {
        wordsFragmentRvWords.adapter = WordsRVAdapter(getDummyWords(requireContext()))
        wordsFragmentRvWords.addItemDecoration(
            SpacingItemDecoration(
                itemSpacingTop = 5,
                itemSpacingLeft = 10,
                itemSpacingBottom = 5,
                itemSpacingRight = 10,
                topMostSpacing = 10,
                bottomMostSpacing = 90
            )
        )
    }


}