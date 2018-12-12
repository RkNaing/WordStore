package com.devshub.rk.wordsstore.ui.viewholders

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.data.model.Word
import com.devshub.rk.wordsstore.extensions.addSubView

/**
 * Created by ZMN on 12/7/18.
 **/
class WordHolder private constructor(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(parent: ViewGroup) = WordHolder(parent.addSubView(R.layout.item_word))
    }

    private val tvWordTitle: AppCompatTextView by lazy {
        itemView.findViewById<AppCompatTextView>(R.id.itemWordTvTitle)
    }

    private val tvWordDesc: AppCompatTextView by lazy {
        itemView.findViewById<AppCompatTextView>(R.id.itemWordTvDesc)
    }

    fun bindWord(word: Word) {
        tvWordTitle.text = word.title
        tvWordDesc.text = word.description
    }

}