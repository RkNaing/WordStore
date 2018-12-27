package com.devshub.rk.wordsstore.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.ui.adapter.WelcomePagerAdapter
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {

    companion object {

        private const val EXTRA_LAUNCH_TYPE = "isManualLaunch"

        fun getWelcomeIntent(context: Context, isManualLaunch: Boolean = false): Intent {
            val intent = Intent(context, WelcomeActivity::class.java)
            intent.putExtra(EXTRA_LAUNCH_TYPE, isManualLaunch)
            return intent
        }

    }

    private val isManualLaunch by lazy {
        intent?.getBooleanExtra(EXTRA_LAUNCH_TYPE, false) ?: false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val welcomePagerAdapter = WelcomePagerAdapter(supportFragmentManager)
        activityWelcomePager.adapter = welcomePagerAdapter
        activityWelcomeSlideIndicatorContainer.setViewPager(activityWelcomePager)
        activityWelcomePager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                activityWelcomeBtnNext.text = if (position == (welcomePagerAdapter.count - 1)) {
                    activityWelcomeBtnSkip.visibility = View.GONE
                    getString(R.string.action_got_it)
                } else {
                    activityWelcomeBtnSkip.visibility = View.VISIBLE
                    getString(R.string.action_next)
                }
            }

        })

        activityWelcomeBtnSkip.setOnClickListener {
            goToMainActivity()
        }

        activityWelcomeBtnNext.setOnClickListener {

            if (activityWelcomePager.currentItem < (welcomePagerAdapter.count - 1)) {
                activityWelcomePager.currentItem += 1
            } else {
                goToMainActivity()
            }
        }
    }

    private fun goToMainActivity() {
        if (isManualLaunch) {
            finish()
        } else {
            val homeIntent = Intent(this, MainActivity::class.java)
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(homeIntent)
            finish()
        }
    }

}