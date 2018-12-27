package com.devshub.rk.wordsstore.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.ui.activities.WelcomeActivity
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import kotlinx.android.synthetic.main.fragment_about.*

/**
 * Created by ZMN on 12/24/18.
 **/
class AboutFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_about

    override fun onViewReady(view: View, savedInstanceState: Bundle?) {

        about_app_intro_container.setOnClickListener {
            startActivity(WelcomeActivity.getWelcomeIntent(requireContext(), true))
        }

        about_lib_container.setOnClickListener {
            OssLicensesMenuActivity.setActivityTitle(getString(R.string.title_open_source_licenses))
            startActivity(
                Intent(
                    requireActivity(),
                    OssLicensesMenuActivity::class.java
                )
            )

        }

        about_assets_license_container.setOnClickListener {
            val message = SpannableString(getString(R.string.about_dialog_msg_asset_license))
            Linkify.addLinks(message, Linkify.WEB_URLS)

            val dialog = AlertDialog.Builder(requireActivity())
                .setTitle(R.string.about_dialog_title_assets_license)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .create()

            dialog.show()
            val messageTv = dialog.findViewById<TextView>(android.R.id.message)
            messageTv?.movementMethod = LinkMovementMethod.getInstance()
        }


    }

}