package com.icarus.calculator.customView

import a.icarus.utils.SpUtil
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.icarus.calculator.R


class CalculatorSetButton(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var tint0: Int
    private var tint1: Int
    private var textView: TextView
    private var iconView: ImageView
    var id: String
    var state = false
        set(value) {
            field = value
            setColor(state)
        }
    private var checkable: Boolean

    init {
        LayoutInflater.from(context).inflate(R.layout.set_button, this) as ConstraintLayout
        val typedArray =
            context.obtainStyledAttributes(
                attrs,
                R.styleable.CalculatorSetButton
            )
        val text = typedArray.getString(R.styleable.CalculatorSetButton_set_text) ?: ""
        id = text
        tint0 = typedArray.getColor(R.styleable.CalculatorSetButton_tintColor_0, Color.WHITE)
        tint1 = typedArray.getColor(R.styleable.CalculatorSetButton_tintColor_1, Color.WHITE)
        checkable = typedArray.getBoolean(R.styleable.CalculatorSetButton_set_checkable, false)
        textView = findViewById(R.id.text)
        iconView = findViewById(R.id.icon)
        textView.text = text
        val iconId = typedArray.getResourceId(R.styleable.CalculatorSetButton_set_icon, 0)
        if(iconId!=0){
            iconView.setImageResource(iconId)
        }else{
            iconView.visibility = GONE
        }
        typedArray.recycle()
        setColor(state)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.run {
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    state = !state
                    parent.requestDisallowInterceptTouchEvent(true)
                }
                MotionEvent.ACTION_UP -> if (!checkable) {
                    state = !state
                    performClick()
                } else {
                    SpUtil.putBoolean(id, state)
                }
                else -> Unit
            }
        }
        setColor(state)
        return true
    }

    private fun setColor(state: Boolean) {
        if (!state) {
            textView.setTextColor(tint0)
            iconView.setColorFilter(tint0)
            setBackgroundResource(R.drawable.button_white)
        } else {
            textView.setTextColor(tint1)
            iconView.setColorFilter(tint1)
            setBackgroundResource(R.drawable.button_blue)
        }
    }
    fun onResume(){
        if (checkable){
            state = SpUtil.getBoolean(id)
            setColor(state)
        }
    }
}