package com.devshub.rk.wordsstore.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.ui.activities.WelcomeActivity
import com.devshub.rk.wordsstore.utils.PREF_THEME
import com.devshub.rk.wordsstore.utils.PreferenceHelper
import com.devshub.rk.wordsstore.utils.ThemeHelper
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import kotlinx.android.synthetic.main.fragment_about.*
import timber.log.Timber

/**
 * Created by ZMN on 12/24/18.
 **/
class AboutFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_about

    override fun onViewReady(view: View, savedInstanceState: Bundle?) {

        about_theme_container.setOnClickListener {
            context?.let {
                val preferenceHelper = PreferenceHelper.getInstance(it)
                val currentTheme = preferenceHelper
                    .getStringPref(PREF_THEME, ThemeHelper.LIGHT_MODE)

                val selectedPosition = when (currentTheme) {
                    ThemeHelper.LIGHT_MODE -> 0
                    ThemeHelper.DARK_MODE -> 1
                    ThemeHelper.DEFAULT_MODE -> if (Build.VERSION.SDK_INT >= 21) 2 else 0
                    else -> -1
                }

                AlertDialog.Builder(it)
                    .setTitle(R.string.about_dialog_title_select_theme)
                    .setSingleChoiceItems(
                        R.array.theme_list_array,
                        selectedPosition
                    ) { dialog, position ->
                        val selectedTheme = when (position) {
                            0 -> ThemeHelper.LIGHT_MODE
                            1 -> ThemeHelper.DARK_MODE
                            else -> ThemeHelper.DEFAULT_MODE
                        }
                        Timber.d("Selected Theme : $selectedTheme")
                        preferenceHelper.setStringPref(PREF_THEME, selectedTheme)
                        ThemeHelper.applyTheme(selectedTheme)
                        dialog.dismiss()
                    }.show()
            }
        }

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

        about_visit_devs_hub.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse("https://www.devshub.co/")
            startActivity(
                Intent.createChooser(
                    browserIntent,
                    getString(R.string.about_lbl_visit_devs_hub)
                )
            )
        }

        about_feedback_container.setOnClickListener {
            val mailIntent = Intent(Intent.ACTION_SENDTO)
            mailIntent.data =
                Uri.parse("mailto:devshub15@gmail.com?subject=${Uri.encode(getString(R.string.about_lbl_feedback_subject))}")
            startActivity(
                Intent.createChooser(
                    mailIntent,
                    getString(R.string.about_feedback_chooser_title)
                )
            )
        }

    }

}