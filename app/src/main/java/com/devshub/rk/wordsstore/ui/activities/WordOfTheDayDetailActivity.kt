package com.devshub.rk.wordsstore.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.data.model.WordWithCategory
import com.devshub.rk.wordsstore.extensions.setTextAsync
import kotlinx.android.synthetic.main.activity_word_of_day_detail.*

/**
 * Created by ZMN on 2019-11-18.
 **/
class WordOfTheDayDetailActivity : AppCompatActivity() {

    companion object {

        private const val EXTRA_WORD = "WORD_WITH_CATEGORY_WORD_OF_DAY"

        fun getIntent(wordWithCategory: WordWithCategory, context: Context): Intent {
            val intent = Intent(context, WordOfTheDayDetailActivity::class.java)
            intent.putExtra(EXTRA_WORD, wordWithCategory)
            return intent
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_of_day_detail)

        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        intent.getParcelableExtra<WordWithCategory>(EXTRA_WORD)?.let {

            wordOfTheDayTvTitle.setTextAsync(it.word.title)
            wordOfTheDayTvDescription.movementMethod = ScrollingMovementMethod()
            wordOfTheDayTvDescription.setTextAsync(it.word.description)
            wordOfTheDayTvCategoryTitle.setTextAsync(it.wordCategory.title)

            wordOfTheDayBtnDismiss.setOnClickListener { finish() }

            return@onCreate
        }

        finish()
    }

}