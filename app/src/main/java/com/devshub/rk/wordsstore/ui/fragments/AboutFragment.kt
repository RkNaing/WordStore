package com.devshub.rk.wordsstore.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.devshub.rk.wordsstore.R
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import kotlinx.android.synthetic.main.fragment_about.*

/**
 * Created by ZMN on 12/24/18.
 **/
class AboutFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_about

    override fun onViewReady(view: View, savedInstanceState: Bundle?) {
        about_iv_app_logo.setOnClickListener {
            OssLicensesMenuActivity.setActivityTitle(getString(R.string.title_open_source_licenses))
            startActivity(
                Intent(
                    requireActivity(),
                    OssLicensesMenuActivity::class.java
                )
            )

        }
    }

}