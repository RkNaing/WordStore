/*
 * Copyright (C) 2019 WordsStore
 *
 * Created by		:	Rahul Kumar
 * Last Modified	:	28 Nov 2019 03:06:11 PM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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