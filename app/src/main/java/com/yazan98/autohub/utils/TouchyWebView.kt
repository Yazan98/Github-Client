package com.yazan98.autohub.utils

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import androidx.core.view.MotionEventCompat.getPointerCount
import androidx.core.view.MotionEventCompat.findPointerIndex
import android.view.MotionEvent
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.view.MotionEventCompat


open class TouchyWebView(context: Context?): WebView(context) {

    constructor(context: Context, atts: AttributeSet): this(context) {

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (MotionEventCompat.findPointerIndex(event, 0) === -1) {
            return super.onTouchEvent(event)
        }

        if (event.pointerCount >= 2) {
            requestDisallowInterceptTouchEvent(true)
        } else {
            requestDisallowInterceptTouchEvent(false)
        }

        return super.onTouchEvent(event)
    }

    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
        requestDisallowInterceptTouchEvent(true)
    }

}