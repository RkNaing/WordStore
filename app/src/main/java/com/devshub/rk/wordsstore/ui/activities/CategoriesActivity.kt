package com.devshub.rk.wordsstore.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.ui.adapter.CategoriesRVAdapter
import com.devshub.rk.wordsstore.ui.fragments.SaveCategoryDialogFragment
import com.devshub.rk.wordsstore.utils.SpacingItemDecoration
import com.devshub.rk.wordsstore.utils.getDummyCategories
import kotlinx.android.synthetic.main.activity_categories.*

/**
 * Created by ZMN on 12/10/18.
 **/
class CategoriesActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.lbl_categories)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        categoriesRV.adapter = CategoriesRVAdapter(getDummyCategories())
        categoriesRV.addItemDecoration(
            SpacingItemDecoration(
                itemSpacingTop = 5,
                itemSpacingLeft = 10,
                itemSpacingBottom = 5,
                itemSpacingRight = 10,
                topMostSpacing = 10,
                bottomMostSpacing = 90
            )
        )

        categoriesFabAdd.setOnClickListener {
            val saveCategoryDialog = SaveCategoryDialogFragment()
            saveCategoryDialog.show(supportFragmentManager, saveCategoryDialog.tag)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            android.R.id.home -> {
                finish()
                false
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}