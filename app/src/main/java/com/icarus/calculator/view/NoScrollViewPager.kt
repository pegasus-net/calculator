package com.icarus.calculator.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NoScrollViewPager(context: Context, attr: AttributeSet?) : ViewPager(context, attr) {
    constructor(context: Context) : this(context, null)

    var forbidScroll = true

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return !forbidScroll && super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return !forbidScroll &&super.onInterceptTouchEvent(ev)
    }
    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, !forbidScroll)
    }
}