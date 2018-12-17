package com.devshub.rk.wordsstore.utils

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by ZMN on 12/7/18.
 **/

/**
 *  Spacing item decoration for RecyclerView
 *
 *  @param itemSpacingTop Space in "DP" on RecyclerView Item Top
 *  @param itemSpacingLeft Space in "DP" on RecyclerView Item Left
 *  @param itemSpacingBottom Space in "DP" on RecyclerView Item Bottom
 *  @param itemSpacingRight Space in "DP" on RecyclerView Item Right
 *
 */
class SpacingItemDecoration(

    itemSpacingTop: Int = 0,

    itemSpacingLeft: Int = 0,

    itemSpacingBottom: Int = 0,

    itemSpacingRight: Int = 0

) : RecyclerView.ItemDecoration() {

    private val itemSpacingTopPx = dpToPx(itemSpacingTop)
    private val itemSpacingLeftPx = dpToPx(itemSpacingLeft)
    private val itemSpacingBottomPx = dpToPx(itemSpacingBottom)
    private val itemSpacingRightPx = dpToPx(itemSpacingRight)


    constructor(itemSpacing: Int) : this(itemSpacing, itemSpacing, itemSpacing, itemSpacing)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.set(itemSpacingLeftPx, itemSpacingTopPx, itemSpacingRightPx, itemSpacingBottomPx)

    }

    private fun dpToPx(dp: Int): Int {
        val displayMetric = Resources.getSystem().displayMetrics
        val px = dp * (displayMetric.densityDpi / 160f)
        return px.toInt()
    }

}