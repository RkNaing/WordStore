/*
 * Copyright (C) 2019 WordsStore
 *
 * Created by		:	Rahul Kumar
 * Last Modified	:	28 Nov 2019 03:06:11 PM
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
import androidx.work.ExistingPeriodicWorkPolicy
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.extensions.getDate
import com.devshub.rk.wordsstore.extensions.getFormattedDate
import com.devshub.rk.wordsstore.extensions.successToast
import com.devshub.rk.wordsstore.ui.activities.WelcomeActivity
import com.devshub.rk.wordsstore.utils.*
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.android.synthetic.main.fragment_about.*
import timber.log.Timber
import java.util.*

class AboutFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_about

    override fun onViewReady(view: View, savedInstanceState: Bundle?) {

        about_noti_time_container.setOnClickListener onNotiTimeClicked@{

            val currentFragmentManager = fragmentManager ?: return@onNotiTimeClicked
            val appContext = context ?: return@onNotiTimeClicked

            val preferenceHelper = PreferenceHelper.getInstance(appContext)
            val notiTime = preferenceHelper.getStringPref(PREF_WOD_NOTI_TIME, DEFAULT_WOD_NOTI_TIME)
            val calendar = Calendar.getInstance()
            calendar.time = notiTime.getDate(TIME_FORMAT_HH_MM_SS) ?: Date()

            val onTimeSetListener =
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute, second ->

                    val selectedCalendar = Calendar.getInstance()
                    selectedCalendar[Calendar.HOUR_OF_DAY] = hourOfDay
                    selectedCalendar[Calendar.MINUTE] = minute
                    selectedCalendar[Calendar.SECOND] = second

                    val updatedWODNotiTime = selectedCalendar.getFormattedDate(TIME_FORMAT_HH_MM_SS)
                        ?: DEFAULT_WOD_NOTI_TIME
                    if (notiTime == updatedWODNotiTime) return@OnTimeSetListener

                    preferenceHelper.setStringPref(PREF_WOD_NOTI_TIME, updatedWODNotiTime)

                    setWordOfDayNotificationWork(appContext, ExistingPeriodicWorkPolicy.REPLACE)


                    appContext.successToast(
                        getString(
                            R.string.about_msg_wod_noti_set, selectedCalendar.getFormattedDate(
                                TIME_FORMAT_hh_MM_SS_z
                            )
                        )
                    )
                }

            val timePickerDialog: TimePickerDialog = TimePickerDialog.newInstance(
                onTimeSetListener,
                calendar[Calendar.HOUR_OF_DAY],
                calendar[Calendar.MINUTE],
                calendar[Calendar.SECOND],
                false
            )
            timePickerDialog.isThemeDark = ThemeHelper.isDarkTheme
            timePickerDialog.title = getString(R.string.about_lbl_word_of_day_noti_time)
            timePickerDialog.vibrate(true)
            timePickerDialog.show(currentFragmentManager, "WOD_NOTI_TIME_PICKER")

        }

        about_theme_container.setOnClickListener onThemeClicked@{
            val appContext = context ?: return@onThemeClicked
            val preferenceHelper = PreferenceHelper.getInstance(appContext)
            val currentTheme = preferenceHelper
                .getStringPref(PREF_THEME, ThemeHelper.DARK_MODE)

            val selectedPosition = when (currentTheme) {
                ThemeHelper.LIGHT_MODE -> 0
                ThemeHelper.DARK_MODE -> 1
                ThemeHelper.DEFAULT_MODE -> if (Build.VERSION.SDK_INT >= 21) 2 else 0
                else -> -1
            }

            AlertDialog.Builder(appContext)
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

        about_app_intro_container.setOnClickListener {
            context?.let {
                startActivity(WelcomeActivity.getWelcomeIntent(it, true))
            }
        }

        about_lib_container.setOnClickListener {
            context?.let {
                OssLicensesMenuActivity.setActivityTitle(getString(R.string.title_open_source_licenses))
                startActivity(
                    Intent(
                        it,
                        OssLicensesMenuActivity::class.java
                    )
                )
            }
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