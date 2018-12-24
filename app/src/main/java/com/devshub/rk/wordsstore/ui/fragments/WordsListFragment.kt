package com.devshub.rk.wordsstore.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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

    private val mainViewModel by lazy { ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java) }

    override fun getLayoutId() = R.layout.fragment_words

    override fun onViewReady(view: View, savedInstanceState: Bundle?) {

        wordsFragmentSwipeRefreshLayout.setOnRefreshListener {
            wordsFragmentSwipeRefreshLayout.isRefreshing = false
            mainViewModel.getAllWords()
        }

        val adapter = WordsRVAdapter { wordWithCategory ->
            SaveWordDialogFragment.show(requireFragmentManager(), wordWithCategory)
        }

        setHasOptionsMenu(true)

        wordsFragmentRvWords.adapter = adapter
        wordsFragmentRvWords.addItemDecoration(
            SpacingItemDecoration(
                itemSpacingTop = 5,
                itemSpacingLeft = 10,
                itemSpacingBottom = 5,
                itemSpacingRight = 10
            )
        )

        with(mainViewModel) {

            getAllWords()

            words.observe(this@WordsListFragment, Observer { it ->
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

//            categoriesCount.observe(this@WordsListFragment, Observer { it ->
//                fabVisibility.postValue(it > 0)
//            })

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.word_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId ?: 0) {
            R.id.wordList_filter_categories -> {

                activity?.let { a ->
                    val categoriesListBottomSheet =
                        CategoryChooserBottomSheetDialogFragment.createInstance(getString(R.string.lbl_filter_by_category)) {
                            mainViewModel.filterByCategoryId(it.id)
                        }

                    categoriesListBottomSheet.show(
                        a.supportFragmentManager,
                        categoriesListBottomSheet.tag
                    )
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}