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

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.extensions.infoToast
import com.devshub.rk.wordsstore.ui.fragments.*
import com.devshub.rk.wordsstore.ui.viewmodels.MainViewModel
import com.devshub.rk.wordsstore.ui.widgets.WordWidget
import com.devshub.rk.wordsstore.utils.PREF_LAST_NOTIFIED_TIME
import com.devshub.rk.wordsstore.utils.PreferenceHelper
import com.judemanutd.autostarter.AutoStartPermissionHelper
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val mainViewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }

    private var wordsListFragment = WordsListFragment()
    private var categoriesListFragment = CategoriesListFragment()
    private var aboutFragment = AboutFragment()
    private var currentFragment: Fragment = wordsListFragment

    private var canAddWords = true
    private var doubleBackToExitPressedOnce = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(mainToolbar)
        // Observe ViewModel values
        with(mainViewModel) {

            widgetWordLiveData.observe(this@MainActivity, Observer {
                WordWidget.refreshWordWidget(this@MainActivity, it)
            })

            categoriesCount.observe(this@MainActivity, Observer { categoriesCount ->
                canAddWords = categoriesCount > 0
                if (currentFragment == wordsListFragment && !canAddWords) {
                    infoToast(R.string.msg_add_words_add_categories)
                    mainBottomNavigationView.selectedItemId = R.id.main_nav_categories
                }
            })

        }

        // Setup bottom navigation view
        mainBottomNavigationView.setOnNavigationItemSelectedListener {
            mainViewModel.currentTabMenuId = it.itemId
            return@setOnNavigationItemSelectedListener when (it.itemId) {
                R.id.main_nav_words -> {
                    if (canAddWords) {
                        navigateTo(wordsListFragment)
                        supportActionBar?.title = getString(R.string.main_lbl_title_words)
                        mainAddWordFab.show()
                        mainAddWordFab.setOnClickListener {
                            SaveWordDialogFragment.show(supportFragmentManager)
                        }
                        true
                    } else {
                        infoToast(R.string.msg_add_words_add_categories)
                        mainBottomNavigationView.selectedItemId = R.id.main_nav_categories
                        false
                    }

                }
                R.id.main_nav_categories -> {
                    navigateTo(categoriesListFragment)
                    supportActionBar?.title = getString(R.string.main_lbl_title_categories)
                    mainAddWordFab.show()
                    mainAddWordFab.setOnClickListener {
                        SaveCategoryDialogFragment.show(supportFragmentManager)
                    }
                    true
                }
                R.id.main_nav_about -> {
                    navigateTo(aboutFragment)
                    supportActionBar?.title = getString(R.string.main_lbl_title_about)
                    mainAddWordFab.hide()
                    mainAddWordFab.setOnClickListener { }
                    true
                }
                else -> false
            }
        }

        if (savedInstanceState == null) {
            // Initial setup for fragments
            supportFragmentManager.commitNow(true) {
                add(R.id.mainFragmentContainer, aboutFragment, aboutFragment.clazzTag)
                hide(aboutFragment)

                add(
                    R.id.mainFragmentContainer,
                    categoriesListFragment,
                    categoriesListFragment.clazzTag
                )
                hide(categoriesListFragment)

                add(R.id.mainFragmentContainer, wordsListFragment, wordsListFragment.clazzTag)
            }
        } else {
            wordsListFragment = supportFragmentManager.findFragmentByTag(wordsListFragment.clazzTag)
                    as? WordsListFragment ?: WordsListFragment()

            categoriesListFragment =
                supportFragmentManager.findFragmentByTag(categoriesListFragment.clazzTag)
                        as? CategoriesListFragment ?: CategoriesListFragment()

            aboutFragment = supportFragmentManager.findFragmentByTag(aboutFragment.clazzTag)
                    as? AboutFragment ?: AboutFragment()
        }



        mainBottomNavigationView.selectedItemId = mainViewModel.currentTabMenuId

        manageAutoStart()

    }

    override fun onBackPressed() {

        val exit = {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                finish()
            } else {
                doubleBackToExitPressedOnce = true
                infoToast(R.string.msg_double_back_to_exit)
                Handler().postDelayed({

                }, 2000)
            }

        }

        when (currentFragment) {
            aboutFragment -> mainBottomNavigationView.selectedItemId = R.id.main_nav_categories
            categoriesListFragment -> {
                if (canAddWords) {
                    mainBottomNavigationView.selectedItemId = R.id.main_nav_words
                } else {
                    exit()
                }
            }
            wordsListFragment -> {
                exit()
            }
            else -> {
                exit()
            }
        }
    }

    private fun navigateTo(destination: Fragment) {

        if (destination == currentFragment) {
            Timber.d("Skipping fragment show/hide for same destination and fragment!")
            return
        }

        supportFragmentManager.commitNow(true) {
            setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            hide(currentFragment)
            show(destination)
            currentFragment = destination
        }
    }

    private fun manageAutoStart() {
        val autoStartPermissionHelper = AutoStartPermissionHelper.getInstance()
        if (autoStartPermissionHelper.isAutoStartPermissionAvailable(applicationContext)
        ) {
            Timber.i("AutoStartPermission is available")

            val currentTimeMillis = System.currentTimeMillis()
            val preferenceHelper = PreferenceHelper.getInstance(applicationContext)
            val lastNotifiedTime = preferenceHelper
                .getLongPref(PREF_LAST_NOTIFIED_TIME, currentTimeMillis)

            val timestampDiff = currentTimeMillis - lastNotifiedTime
            val diffInHours = if (timestampDiff > 0L) {
                timestampDiff / (1000 * 60 * 60)
            } else {
                preferenceHelper.setLongPref(PREF_LAST_NOTIFIED_TIME, currentTimeMillis)
                -1
            }

            if (diffInHours < 0 || diffInHours >= 24) {

                AlertDialog.Builder(this@MainActivity)
                    .setTitle(R.string.dialog_title_auto_start)
                    .setMessage(R.string.dialog_msg_auto_start)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok) { dialog, _ ->
                        val requestStatus =
                            autoStartPermissionHelper.getAutoStartPermission(applicationContext)
                        Timber.i("Is AutoStartPermission request success? : $requestStatus")
                        dialog.dismiss()
                    }
                    .show()

            }

        }
    }

}