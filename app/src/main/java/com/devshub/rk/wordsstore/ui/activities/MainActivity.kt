package com.devshub.rk.wordsstore.ui.activities

import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.ui.fragments.*
import com.devshub.rk.wordsstore.ui.viewmodels.MainViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        var screenHeight: Int = 0
    }

    private lateinit var navHandler: () -> Unit

    private lateinit var currentFragmentTag: String

    private val wordsListFragment = WordsListFragment()

    private val manageCategoriesFragment = ManageCategoriesFragment()

    private var totalCategoriesCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(mainBottomAppBar)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenHeight = displayMetrics.heightPixels

        val mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.categoriesCount.observe(this, Observer { categoriesCount ->
            categoriesCount?.let {
                val shouldPerformUpdate = !(totalCategoriesCount > 0 && it > 0)
                totalCategoriesCount = it

                if (shouldPerformUpdate) {
                    if (currentFragmentTag == WordsListFragment.TAG && it < 1) {
                        showManageCategoriesFragment()
                    } else {
                        updateNavIcon()
                        invalidateOptionsMenu()
                    }
                }
            }
        })

        showWordsListFragment()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val categoriesItem = menu!!.findItem(R.id.main_categories)
        val isCategoriesEmpty = totalCategoriesCount < 1
        categoriesItem.isVisible = currentFragmentTag != ManageCategoriesFragment.TAG && !isCategoriesEmpty
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            android.R.id.home -> {
                navHandler()
                false
            }
            R.id.main_categories -> {
                showManageCategoriesFragment()
                false
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onBackPressed() {
        if (currentFragmentTag == ManageCategoriesFragment.TAG && totalCategoriesCount > 0) {
            showWordsListFragment()
        } else {
            finish()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        Handler().post {
            supportFragmentManager.transaction(allowStateLoss = true) {
                setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                replace(R.id.mainFragmentContainer, fragment, currentFragmentTag)
                addToBackStack(null)
            }
        }
    }

    private fun showWordsListFragment() {
        currentFragmentTag = WordsListFragment.TAG

        Handler().postDelayed({
            runOnUiThread {
                updateNavIcon()
            }
        }, 100)

        navHandler = {
            val categoriesListBottomSheet =
                CategoryChooserBottomSheetDialogFragment.createInstance(getString(R.string.lbl_filter_by_category)) {
                    Toast.makeText(applicationContext, it.title, Toast.LENGTH_SHORT).show()
                }

            categoriesListBottomSheet.show(
                supportFragmentManager,
                categoriesListBottomSheet.tag
            )
        }

        invalidateOptionsMenu()
        replaceFragment(wordsListFragment)
        mainTvScreenTitle.text = getString(R.string.app_name)
        mainAddWordFab.setOnClickListener {
            val saveWordDialog = SaveWordDialogFragment.createInstance()
            saveWordDialog.show(supportFragmentManager, saveWordDialog.tag)
        }
    }

    private fun showManageCategoriesFragment() {
        currentFragmentTag = ManageCategoriesFragment.TAG

        Handler().postDelayed({
            runOnUiThread {
                updateNavIcon()
            }
        }, 100)

        navHandler = {
            showWordsListFragment()
        }

        invalidateOptionsMenu()
        replaceFragment(manageCategoriesFragment)
        mainTvScreenTitle.text = getString(R.string.lbl_categories)
        mainAddWordFab.setOnClickListener {
            val saveCategoryDialog = SaveCategoryDialogFragment.createInstance()
            saveCategoryDialog.show(supportFragmentManager, saveCategoryDialog.tag)
        }
    }

    private fun updateNavIcon() {

        val isShowingWordsList = currentFragmentTag == WordsListFragment.TAG

        mainBottomAppBar.navigationIcon = if (totalCategoriesCount < 1) {
            null
        } else {
            @DrawableRes val navigationIconRes: Int =
                if (isShowingWordsList) R.drawable.ic_filter_list_white_24dp else R.drawable.ic_arrow_back_white_24dp

            ContextCompat.getDrawable(this, navigationIconRes)
        }

        mainBottomAppBar.fabAlignmentMode =
                if (isShowingWordsList) BottomAppBar.FAB_ALIGNMENT_MODE_CENTER else BottomAppBar.FAB_ALIGNMENT_MODE_END

    }

}
