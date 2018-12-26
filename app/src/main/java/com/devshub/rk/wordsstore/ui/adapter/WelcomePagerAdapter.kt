package com.devshub.rk.wordsstore.ui.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.devshub.rk.wordsstore.data.model.WelcomeTopic
import com.devshub.rk.wordsstore.ui.fragments.WelcomeSlideFragment

class WelcomePagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val topics = WelcomeTopic.getTopics()

    override fun getItem(position: Int) = WelcomeSlideFragment.getInstance(topics[position])

    override fun getCount() = topics.size

}