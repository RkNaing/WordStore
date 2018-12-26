package com.devshub.rk.wordsstore.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.data.model.WelcomeTopic
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
            welcomeSlideTvTitle.text = getString(it.title)
            welcomeSlideTvDescription.text = getString(it.description)
            welcomeSlideIvFoto.setImageDrawable(ContextCompat.getDrawable(requireContext(), it.logo))
        }
    }
}