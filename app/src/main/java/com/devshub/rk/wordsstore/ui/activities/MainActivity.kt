package com.devshub.rk.wordsstore.ui.activities

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.ui.fragments.CategoriesFilterBottomSheetDialogFragment
import com.devshub.rk.wordsstore.ui.fragments.ManageCategoriesFragment
import com.devshub.rk.wordsstore.ui.fragments.SaveCategoryDialogFragment
import com.devshub.rk.wordsstore.ui.fragments.WordsListFragment
import com.google.android.material.bottomappbar.BottomAppBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navHandler: () -> Unit

    private lateinit var currentFragmentTag: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(mainBottomAppBar)

        showWordsListFragment()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        if (currentFragmentTag == ManageCategoriesFragment.TAG) {
            val categoriesItem = menu!!.findItem(R.id.main_categories)
            categoriesItem.isVisible = false
        }
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

    private fun replaceFragment(fragment: Fragment) {
        Handler().post {
            supportFragmentManager.transaction(allowStateLoss = true) {
                setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                replace(R.id.mainFragmentContainer, fragment, currentFragmentTag)
            }
        }
    }

    private fun showWordsListFragment() {
        Handler().postDelayed({
            runOnUiThread {
                mainBottomAppBar.navigationIcon =
                        ContextCompat.getDrawable(this, R.drawable.ic_filter_list_white_24dp)
                mainBottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            }
        }, 100)

        navHandler = {
            val categoriesListBottomSheet = CategoriesFilterBottomSheetDialogFragment()
            categoriesListBottomSheet.show(
                supportFragmentManager,
                categoriesListBottomSheet.tag
            )
        }
        currentFragmentTag = WordsListFragment.TAG
        invalidateOptionsMenu()
        replaceFragment(WordsListFragment())
        mainTvScreenTitle.text = getString(R.string.app_name)
        mainAddWordFab.setOnClickListener {
            Toast.makeText(this, "Add Word", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showManageCategoriesFragment() {
        Handler().postDelayed({
            runOnUiThread {
                mainBottomAppBar.navigationIcon =
                        ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp)
                mainBottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            }
        }, 100)

        navHandler = {
            showWordsListFragment()
        }
        currentFragmentTag = ManageCategoriesFragment.TAG
        invalidateOptionsMenu()
        replaceFragment(ManageCategoriesFragment())
        mainTvScreenTitle.text = getString(R.string.lbl_categories)
        mainAddWordFab.setOnClickListener {
            val saveCategoryDialog = SaveCategoryDialogFragment.createInstance()
            saveCategoryDialog.show(supportFragmentManager, saveCategoryDialog.tag)
        }
    }

}
