package com.devshub.rk.wordsstore.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.devshub.rk.wordsstore.data.model.WordWithCategory
import com.devshub.rk.wordsstore.ui.viewholders.WordHolder
import com.devshub.rk.wordsstore.utils.WordWithCategoryItemClickCallback

/**
 * Created by ZMN on 12/7/18.
 **/
class WordsRVAdapter(private val clickCallback: WordWithCategoryItemClickCallback) :
    PagedListAdapter<WordWithCategory, WordHolder>(WordWithCategory.diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
        return WordHolder.create(parent, clickCallback)
    }

    override fun onBindViewHolder(holder: WordHolder, position: Int) {
        getItem(position)?.let {
            holder.bindWord(it)
        }
    }
}