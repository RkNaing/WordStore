package com.devshub.rk.wordsstore.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devshub.rk.wordsstore.data.model.Word
import com.devshub.rk.wordsstore.ui.viewholders.WordHolder

/**
 * Created by ZMN on 12/7/18.
 **/
class WordsRVAdapter(var words: MutableList<Word> = mutableListOf()) :
    RecyclerView.Adapter<WordHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
        return WordHolder.create(parent)
    }

    override fun getItemCount() = words.size

    override fun onBindViewHolder(holder: WordHolder, position: Int) {
        holder.bindWord(words[position])
    }
}