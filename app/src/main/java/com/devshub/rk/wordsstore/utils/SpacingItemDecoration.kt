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
 *  @param topMostSpacing Space in "DP" on RecyclerView very first Item Top (Default value is equal to itemSpacingTop)
 *  @param bottomMostSpacing Space in "DP" on RecyclerView last Item Bottom.
 *  Can provide SpacingItemDecoration.NEAREST_ITEM_VIEW_HEIGHT to set the spacing to double height of second last RecyclerView
 *
 */
class SpacingItemDecoration(

    itemSpacingTop: Int = 0,

    itemSpacingLeft: Int = 0,

    itemSpacingBottom: Int = 0,

    itemSpacingRight: Int = 0,

    topMostSpacing: Int = itemSpacingTop,

    bottomMostSpacing: Int = itemSpacingBottom

) : RecyclerView.ItemDecoration() {

    private val itemSpacingTopPx = dpToPx(itemSpacingTop)
    private val itemSpacingLeftPx = dpToPx(itemSpacingLeft)
    private val itemSpacingBottomPx = dpToPx(itemSpacingBottom)
    private val itemSpacingRightPx = dpToPx(itemSpacingRight)

    private val topMostSpacingPx =
        if (topMostSpacing == SpacingItemDecoration.NEAREST_ITEM_VIEW_HEIGHT) topMostSpacing else dpToPx(
            topMostSpacing
        )

    private val bottomMostSpacingPx =
        if (bottomMostSpacing == SpacingItemDecoration.NEAREST_ITEM_VIEW_HEIGHT) bottomMostSpacing else dpToPx(
            bottomMostSpacing
        )

    companion object {
        const val NEAREST_ITEM_VIEW_HEIGHT = -245
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val dataSize = state.itemCount
        val currentPosition = parent.getChildAdapterPosition(view)

        var topSpacing = itemSpacingTopPx
        var bottomSpacing = itemSpacingBottomPx

        if (currentPosition == (dataSize - 1)) {

            bottomSpacing = if (bottomMostSpacingPx == NEAREST_ITEM_VIEW_HEIGHT) {
                view.height
            } else {
                bottomMostSpacingPx
            }

        } else if (currentPosition == 0) {

            topSpacing = if (topMostSpacingPx == NEAREST_ITEM_VIEW_HEIGHT) {
                view.height
            } else {
                topMostSpacingPx
            }

        }

        outRect.set(itemSpacingLeftPx, topSpacing, itemSpacingRightPx, bottomSpacing)

    }

    private fun dpToPx(dp: Int): Int {
        val displayMatric = Resources.getSystem().displayMetrics
        val px = dp * (displayMatric.densityDpi / 160f)
        return px.toInt()
    }

}