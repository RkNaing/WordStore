package com.devshub.rk.wordsstore.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.data.model.WordWithCategory
import com.devshub.rk.wordsstore.data.repositories.wordRepository
import com.devshub.rk.wordsstore.ui.activities.WordOfTheDayDetailActivity
import com.devshub.rk.wordsstore.utils.NOTIFICATION_CHANNEL_ID
import com.devshub.rk.wordsstore.utils.PREF_LAST_NOTIFIED_TIME
import com.devshub.rk.wordsstore.utils.PreferenceHelper
import com.devshub.rk.wordsstore.utils.getRandomNumber

/**
 * Created by ZMN on 2019-10-31.
 **/
class WordOfTheDayWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val appContext = applicationContext

        with(wordRepository) {

            val wordsCount = getWordsCount(appContext)
            if (wordsCount > 0) {
                showNotification(getSingleRandomWordWithCategoryTitle(appContext))
            }
        }

        PreferenceHelper.getInstance(appContext)
            .setLongPref(PREF_LAST_NOTIFIED_TIME, System.currentTimeMillis())

        return Result.success()

    }

    private fun showNotification(wordWithCategory: WordWithCategory) {
        val appContext = applicationContext
        val notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationLargeIcon = BitmapFactory.decodeResource(
            appContext.resources,
            R.drawable.app_logo_256
        )

        val wordOfTheDayIntent = WordOfTheDayDetailActivity.getIntent(wordWithCategory, appContext)
        wordOfTheDayIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            appContext,
            12,
            wordOfTheDayIntent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val notificationBuilder = NotificationCompat.Builder(appContext, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_app_notification)
            .setLargeIcon(notificationLargeIcon)
            .setColor(ContextCompat.getColor(appContext, R.color.colorNotificationTint))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setSound(notificationSoundUri)
            .setContentTitle(wordWithCategory.word.title)
            .setContentText(wordWithCategory.word.description)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText(wordWithCategory.word.description))

        val notificationManager = appContext.getSystemService(Context.NOTIFICATION_SERVICE)
        if (notificationManager !is NotificationManager) {
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                appContext.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(getRandomNumber(4), notificationBuilder.build())
    }
}