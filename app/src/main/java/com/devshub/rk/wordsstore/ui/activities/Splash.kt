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

package com.devshub.rk.wordsstore.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.data.db.AppDB
import com.devshub.rk.wordsstore.extensions.observeOnce
import com.devshub.rk.wordsstore.utils.PREF_IS_INITIAL_LAUNCH
import com.devshub.rk.wordsstore.utils.PreferenceHelper
import com.devshub.rk.wordsstore.utils.setWordOfDayNotificationWork

class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Warm up app database to avoid faulty first time live data callback in MainActivity
        AppDB.getInstance(this).getCategoryDao().categoriesCount().observeOnce(Observer {
            init()
        })

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
    }

    private fun init() {
        Handler().postDelayed({

            setWordOfDayNotificationWork(this)

            val isInitialLaunch =
                PreferenceHelper.getInstance(this).getBooleanPref(PREF_IS_INITIAL_LAUNCH, true)
            val intent = if (isInitialLaunch) {
                PreferenceHelper.getInstance(this).setBooleanPref(PREF_IS_INITIAL_LAUNCH, false)
                WelcomeActivity.getWelcomeIntent(this)
            } else {
                Intent(this, MainActivity::class.java)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }, 1500)
    }

}