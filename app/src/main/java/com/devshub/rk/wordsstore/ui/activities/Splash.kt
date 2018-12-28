package com.devshub.rk.wordsstore.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.data.db.AppDB
import com.devshub.rk.wordsstore.utils.PREF_IS_INITIAL_LAUNCH
import com.devshub.rk.wordsstore.utils.PreferenceHelper

/**
 * Created by ZMN on 12/26/18.
 **/
class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Warm up app database
        AppDB.getInstance(this)
        Handler().postDelayed({
            val isInitialLaunch = PreferenceHelper.getInstance(this).getBooleanPref(PREF_IS_INITIAL_LAUNCH, true)
            val intent = if (isInitialLaunch) {
                PreferenceHelper.getInstance(this).setBooleanPref(PREF_IS_INITIAL_LAUNCH, false)
                WelcomeActivity.getWelcomeIntent(this)
            } else {
                Intent(this, MainActivity::class.java)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }, 2000)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
    }

}