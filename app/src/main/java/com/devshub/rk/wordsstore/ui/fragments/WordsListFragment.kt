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

    companion object {
        private const val ALL_WORDS = "Words"
    }

    private val mainViewModel by lazy { ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java) }

    private var currentCategory = ALL_WORDS

    private var shouldShowFilter = false

    override fun getLayoutId() = R.layout.fragment_words

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewReady(view: View, savedInstanceState: Bundle?) {

        wordsFragmentSwipeRefreshLayout.setOnRefreshListener {
            wordsFragmentSwipeRefreshLayout.isRefreshing = false
            currentCategory = ALL_WORDS
            mainViewModel.getAllWords()
        }

        val adapter = WordsRVAdapter { wordWithCategory ->
            SaveWordDialogFragment.show(requireFragmentManager(), wordWithCategory)
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

        with(mainViewModel) {

            getAllWords()

            words.observe(this@WordsListFragment, Observer {
                val wordsCountLabel = "${it.count()} $currentCategory"
                wordsFragmentTvCount.text = wordsCountLabel
                adapter.submitList(it) {
                    if (adapter.itemCount > 0) {
                        wordsFragmentEmptyViewContainer.gone()
                        wordsFragmentTvCount.visible()
                        wordsFragmentRvWords.visible()
                        shouldShowFilter = true
                    } else {
                        wordsFragmentTvEmptyMessage.text =
                                if (currentCategory == ALL_WORDS) {
                                    shouldShowFilter = false
                                    getString(R.string.msg_empty_words)
                                } else {
                                    shouldShowFilter = true
                                    getString(
                                        R.string.msg_empty_words_by_category,
                                        currentCategory
                                    )
                                }
                        wordsFragmentEmptyViewContainer.visible()
                        wordsFragmentTvCount.gone()
                        wordsFragmentRvWords.gone()
                    }
                    activity?.invalidateOptionsMenu()
                }
            })

        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.word_list_menu, menu)
        val filterMenuItem = menu.findItem(R.id.wordList_filter_categories)
        filterMenuItem?.isVisible = shouldShowFilter
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.wordList_filter_categories -> {

                activity?.let { a ->
                    val categoriesListBottomSheet =
                        CategoryChooserBottomSheetDialogFragment.createInstance(getString(R.string.lbl_filter_by_category)) {
                            currentCategory = it.title
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