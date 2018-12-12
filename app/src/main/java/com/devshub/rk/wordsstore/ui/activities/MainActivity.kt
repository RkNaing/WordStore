package com.devshub.rk.wordsstore.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.ui.adapter.WordsRVAdapter
import com.devshub.rk.wordsstore.ui.fragments.CategoriesFilterBottomSheetDialogFragment
import com.devshub.rk.wordsstore.utils.SpacingItemDecoration
import com.devshub.rk.wordsstore.utils.getDummyWords
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainBottomAppBar)


        mainWordsRv.adapter = WordsRVAdapter(getDummyWords(this))
        mainWordsRv.addItemDecoration(
            SpacingItemDecoration(
                itemSpacingTop = 5,
                itemSpacingLeft = 10,
                itemSpacingBottom = 5,
                itemSpacingRight = 10,
                topMostSpacing = 10,
                bottomMostSpacing = 80
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            android.R.id.home -> {
                val categoriesListBottomSheet = CategoriesFilterBottomSheetDialogFragment()
                categoriesListBottomSheet.show(
                    supportFragmentManager,
                    categoriesListBottomSheet.tag
                )
                false
            }
            R.id.main_categories -> {
                startActivity(Intent(this, CategoriesActivity::class.java))
                false
            }
            else -> super.onOptionsItemSelected(item)
        }

    }


}
