package com.icarus.calculator.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView
import androidx.constraintlayout.widget.ConstraintLayout

class ScrollBottom(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    ScrollView(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }
}