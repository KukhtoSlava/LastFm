package com.slavakukhto.lastfm.androidApp.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View

class BaseStatusBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private var mStatusBarHeight: Int = 24

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val resourceId =
            resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            mStatusBarHeight = resources.getDimensionPixelSize(resourceId)
        }
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mStatusBarHeight)
    }
}
