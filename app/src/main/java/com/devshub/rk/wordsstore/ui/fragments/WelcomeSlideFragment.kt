/*
 * Copyright (C) 2019 WordsStore
 *
 * Created by		:	Rahul Kumar
 * Last Modified	:	28 Nov 2019 03:06:10 PM
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

package com.devshub.rk.wordsstore.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.data.model.WelcomeTopic
import com.devshub.rk.wordsstore.extensions.setTextAsync
import kotlinx.android.synthetic.main.fragment_welcome_slide.*

class WelcomeSlideFragment : BaseFragment() {

    companion object {

        private const val ARGS_TOPIC = "ArgWelcomeTopic"

        fun getInstance(topic: WelcomeTopic): WelcomeSlideFragment {
            val fragment = WelcomeSlideFragment()
            val arguments = Bundle().also { it.putParcelable(ARGS_TOPIC, topic) }
            fragment.arguments = arguments
            return fragment
        }
    }

    override fun getLayoutId() = R.layout.fragment_welcome_slide

    override fun onViewReady(view: View, savedInstanceState: Bundle?) {
        val topic: WelcomeTopic? = arguments?.getParcelable(ARGS_TOPIC)
        topic?.let {
            welcomeSlideTvTitle.setTextAsync(getString(it.title))
            welcomeSlideTvDescription.setTextAsync(getString(it.description))
            welcomeSlideIvFoto.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    it.logo
                )
            )
        }
    }
}