package com.devshub.rk.wordsstore.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.extensions.infoToast
import com.devshub.rk.wordsstore.ui.fragments.*
import com.devshub.rk.wordsstore.ui.viewmodels.MainViewModel
import com.devshub.rk.wordsstore.ui.widgets.WordWidget
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val mainViewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }

    private val wordsListFragment = WordsListFragment()
    private val categoriesListFragment = CategoriesListFragment()
    private val aboutFragment = AboutFragment()
    private var currentFragment: Fragment = wordsListFragment

    private var canAddWords = true

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

        // Initial setup for fragments
        supportFragmentManager.transaction {
            add(R.id.mainFragmentContainer, aboutFragment, aboutFragment::class.java.simpleName)
            hide(aboutFragment)

            add(R.id.mainFragmentContainer, categoriesListFragment, categoriesListFragment::class.java.simpleName)
            hide(categoriesListFragment)

            add(R.id.mainFragmentContainer, wordsListFragment, wordsListFragment::class.java.simpleName)
        }

        mainBottomNavigationView.selectedItemId = R.id.main_nav_words


    }

    private fun navigateTo(destination: Fragment) {

        if (destination == currentFragment) {
            Timber.d("Skipping fragment show/hide for same destination and fragment!")
            return
        }

        supportFragmentManager.transaction {
            hide(currentFragment)
            show(destination)
            currentFragment = destination
        }
    }

}