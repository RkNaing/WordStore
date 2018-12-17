package com.devshub.rk.wordsstore.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.extensions.gone
import com.devshub.rk.wordsstore.extensions.visible
import com.devshub.rk.wordsstore.ui.adapter.WordsRVAdapter
import com.devshub.rk.wordsstore.ui.viewmodels.MainViewModel
import com.devshub.rk.wordsstore.utils.SpacingItemDecoration
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

        val adapter = WordsRVAdapter { wordWithCategory ->
            val saveWordDialogFragment = SaveWordDialogFragment.createInstance(wordWithCategory)
            saveWordDialogFragment.show(activity?.supportFragmentManager, saveWordDialogFragment.tag)
        }

        wordsFragmentRvWords.adapter = adapter
        wordsFragmentRvWords.addItemDecoration(
            SpacingItemDecoration(
                itemSpacingTop = 5,
                itemSpacingLeft = 10,
                itemSpacingBottom = 5,
                itemSpacingRight = 10
            )
        )

        val mainViewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.words.observe(this, Observer { it ->
            adapter.submitList(it) {
                if (adapter.itemCount > 0) {
                    wordsFragmentEmptyViewContainer.gone()
                    wordsFragmentRvWords.visible()
                } else {
                    wordsFragmentEmptyViewContainer.visible()
                    wordsFragmentRvWords.gone()
                }
            }
        })
    }


}