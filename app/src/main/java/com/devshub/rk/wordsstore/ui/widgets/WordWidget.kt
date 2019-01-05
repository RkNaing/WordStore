package com.devshub.rk.wordsstore.ui.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RemoteViews
import com.devshub.rk.wordsstore.R
import com.devshub.rk.wordsstore.data.model.WordWithCategory
import com.devshub.rk.wordsstore.data.repositories.wordRepository
import com.devshub.rk.wordsstore.ui.activities.MainActivity
import com.devshub.rk.wordsstore.utils.PREF_IS_WIDGET_SHOWN
import com.devshub.rk.wordsstore.utils.PREF_WIDGET_WORD_ID
import com.devshub.rk.wordsstore.utils.PreferenceHelper

/**
 * Implementation of App Widget functionality.
 */
class WordWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(
                context,
                appWidgetManager,
                appWidgetId
            )
        }
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        context?.let {
            PreferenceHelper.getInstance(it).setBooleanPref(PREF_IS_WIDGET_SHOWN, true)
        }
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        context?.let {
            PreferenceHelper.getInstance(it).setBooleanPref(PREF_IS_WIDGET_SHOWN, false)
            PreferenceHelper.getInstance(it).setLongPref(PREF_WIDGET_WORD_ID, -1)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        if (context != null && intent != null) {

            val wordWidget = ComponentName(context, WordWidget::class.java)
            val appWidgetManager = AppWidgetManager.getInstance(context)

            val updateOperation: (RemoteViews) -> Unit = { views ->
                // Instruct the widget manager to update the widget
                appWidgetManager.updateAppWidget(wordWidget, views)
            }

            if (intent.action == ACTION_RELOAD_WIDGET || intent.action == ACTION_APPWIDGET_UPDATE) {
                loadAndInflateWidgetData(context, updateOperation)
            } else if (intent.action == ACTION_REFRESH_WIDGET) {
                val wordWithCategory: WordWithCategory? = intent.getParcelableExtra(ARG_WORD_WITH_CATEGORY)
                if (wordWithCategory != null) {
                    val views = inflateWidgetData(context, wordWithCategory)
                    appWidgetManager.updateAppWidget(wordWidget, views)
                } else {
                    loadAndInflateWidgetData(context, updateOperation)
                }
            }
        }
    }

    companion object {

        internal const val ACTION_RELOAD_WIDGET = "com.devshub.rk.wordsstore.reload"
        internal const val ACTION_REFRESH_WIDGET = "com.devshub.rk.wordsstore.refresh"
        internal const val ARG_WORD_WITH_CATEGORY = "com.devshub.rk.wordsstore.arg.wordWithCategory"

        internal fun refreshWordWidget(context: Context, wordWithCategory: WordWithCategory?) {
            val preferenceHelper = PreferenceHelper.getInstance(context)
            if (preferenceHelper.getBooleanPref(PREF_IS_WIDGET_SHOWN, false)) {
                val intent = Intent(context, WordWidget::class.java)
                intent.action = ACTION_REFRESH_WIDGET
                intent.putExtra(ARG_WORD_WITH_CATEGORY, wordWithCategory)
                context.sendBroadcast(intent)
            }
        }

        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            loadAndInflateWidgetData(context) { views ->
                appWidgetManager.updateAppWidget(appWidgetId, views)
            }

        }

        internal fun loadAndInflateWidgetData(context: Context, completion: (RemoteViews) -> Unit) {

            with(wordRepository) {
                getWordsCount(context) { wordsCount ->
                    if (wordsCount > 0) {
                        wordRepository.getSingleRandomWordWithCategoryTitle(context) {
                            completion(
                                inflateWidgetData(
                                    context,
                                    it
                                )
                            )
                        }
                    } else {
                        completion(getWidgetEmptyView(context))
                    }

                }
            }
        }

        private fun inflateWidgetData(context: Context, wordWithCategory: WordWithCategory): RemoteViews {
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.word_widget)
            views.setViewVisibility(R.id.widgetWordEmptyViewContainer, View.GONE)
            views.setViewVisibility(R.id.widgetWordContentContainer, View.VISIBLE)
            views.setTextViewText(R.id.widgetWordTvTitle, wordWithCategory.word.title)
            views.setTextViewText(R.id.widgetWordTvDesc, wordWithCategory.word.description)
            views.setTextViewText(R.id.widgetWordCategoryTitleTv, wordWithCategory.wordCategory.title)

            val intent = Intent(context, WordWidget::class.java)
            intent.action = ACTION_RELOAD_WIDGET
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                25,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            views.setOnClickPendingIntent(R.id.widgetWordIvRefresh, pendingIntent)
            views.setOnClickPendingIntent(R.id.widgetWordIvAdd, getMainActivityPendingIntent(context))

            PreferenceHelper.getInstance(context).setLongPref(PREF_WIDGET_WORD_ID, wordWithCategory.word.id)

            return views
        }

        private fun getWidgetEmptyView(context: Context): RemoteViews {
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.word_widget)
            views.setViewVisibility(R.id.widgetWordEmptyViewContainer, View.VISIBLE)
            views.setViewVisibility(R.id.widgetWordContentContainer, View.GONE)
            views.setOnClickPendingIntent(R.id.widgetWordEmptyViewContainer, getMainActivityPendingIntent(context))
            return views
        }

        private fun getMainActivityPendingIntent(context: Context): PendingIntent {
            val intent = Intent(context, MainActivity::class.java)
            return PendingIntent.getActivity(
                context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT
            )
        }

    }
}

