package com.devshub.rk.wordsstore.ui.viewholders

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.data.model.WordWithCategory
import com.devshub.rk.wordsstore.extensions.addSubView
import com.devshub.rk.wordsstore.extensions.setTextAsync
import com.devshub.rk.wordsstore.utils.WordWithCategoryCallback
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_word.*

/**
 * Created by ZMN on 12/7/18.
 **/
class WordHolder private constructor(
    override val containerView: View,
    val clickCallback: WordWithCategoryCallback
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    companion object {
        fun create(parent: ViewGroup, clickCallback: WordWithCategoryCallback): WordHolder {
            val view = parent.addSubView(R.layout.item_word)
            return WordHolder(view, clickCallback)
        }
    }

    fun bindWord(wordWithCategory: WordWithCategory) {
        itemWordTvTitle.setTextAsync(wordWithCategory.word.title)
        itemWordTvDesc.setTextAsync(wordWithCategory.word.description)
        itemWordCategoryTitleTv.setTextAsync(wordWithCategory.wordCategory.title)
        itemView.setOnClickListener { clickCallback(wordWithCategory) }
    }

}