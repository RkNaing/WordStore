package com.devshub.rk.wordsstore.utils

import android.content.Context
import androidx.annotation.IntRange
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.devshub.rk.wordsstore.extensions.getDate
import com.devshub.rk.wordsstore.extensions.toReadableString
import com.devshub.rk.wordsstore.work.WordOfTheDayWorker
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by ZMN on 2019-11-13.
 **/
internal fun getNumberUID(@IntRange(from = 1, to = 999) length: Int): String {
    val random = Random()
    val generatedRandomNumbers: List<Int> = List(length) {
        random.nextInt(10)
    }.shuffled(random)
    return generatedRandomNumbers.joinToString(separator = "")
}

internal fun getRandomNumber(@IntRange(from = 1, to = 999) length: Int): Int =
    getNumberUID(length).toInt()

internal fun setWordOfDayNotificationWork(
    context: Context,
    policy: ExistingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP
) {

    val preferenceHelper = PreferenceHelper.getInstance(context)
    val notiTime = preferenceHelper.getStringPref(PREF_WOD_NOTI_TIME, DEFAULT_WOD_NOTI_TIME)

    val targetCalender = Calendar.getInstance()
    targetCalender.time = notiTime.getDate(TIME_FORMAT_HH_MM_SS) ?: Date()

    val nowCalendar = Calendar.getInstance()
    nowCalendar[Calendar.MILLISECOND] = 0

    targetCalender[Calendar.DATE] = nowCalendar[Calendar.DATE]
    targetCalender[Calendar.MONTH] = nowCalendar[Calendar.MONTH]
    targetCalender[Calendar.YEAR] = nowCalendar[Calendar.YEAR]
    targetCalender[Calendar.MILLISECOND] = 0

    Timber.d("Current Time ${nowCalendar.toReadableString()}")
    Timber.d("Target Time ${targetCalender.toReadableString()}")

    if (nowCalendar.time.after(targetCalender.time)) {
        targetCalender.add(Calendar.DATE, 1)
    }

    val initialDelay = targetCalender.timeInMillis - nowCalendar.timeInMillis
    Timber.d("Current Time ${nowCalendar.toReadableString()}")
    Timber.d("Target Time ${targetCalender.toReadableString()}")
    Timber.d("Initial Delay $initialDelay")


    // Setup WorkManager
    val wordOfTheDayWork =
        PeriodicWorkRequest.Builder(WordOfTheDayWorker::class.java, 24, TimeUnit.HOURS)
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        WORD_OF_THE_DAY_WORK_ID,
        policy,
        wordOfTheDayWork
    )
}