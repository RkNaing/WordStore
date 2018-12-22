package com.devshub.rk.wordsstore.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.ui.viewmodels.MainViewModel
import com.devshub.rk.wordsstore.ui.widgets.WordWidget
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.mainNavigationFragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(mainToolbar)
        setupActionBarWithNavController(navController)

        with(ViewModelProviders.of(this).get(MainViewModel::class.java)) {

            screenTitle.observe(this@MainActivity, Observer { titleStringResId ->
                supportActionBar?.title = getString(titleStringResId)
            })

            fabIcon.observe(this@MainActivity, Observer { fabDrawable ->
                mainAddWordFab.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, fabDrawable))
            })

            fabClickListener.observe(this@MainActivity, Observer {
                mainAddWordFab.setOnClickListener(it)
            })

            fabVisibility.observe(this@MainActivity, Observer { isVisible ->
                if (isVisible) mainAddWordFab.show() else mainAddWordFab.hide()
            })

            widgetWordLiveData.observe(this@MainActivity, Observer {
                WordWidget.refreshWordWidget(this@MainActivity, it)
            })

        }

    }

    override fun onSupportNavigateUp() = navController.navigateUp()

}
