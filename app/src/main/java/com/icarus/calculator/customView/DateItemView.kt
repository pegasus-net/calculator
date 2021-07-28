package com.icarus.calculator.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.icarus.calculator.R

class DateItemView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var itemName: TextView
    private var itemResult: TextView
    private var itemTip: TextView
    private var line: View

    init {
        LayoutInflater.from(context).inflate(R.layout.item_result, this)
        itemName = findViewById(R.id.item_name)
        itemResult = findViewById(R.id.item_result)
        itemTip = findViewById(R.id.item_tip)
        val arr = context.obtainStyledAttributes(attrs, R.styleable.DateItemView)
        itemName.text = arr.getString(R.styleable.DateItemView_item_name)
        itemResult.text = arr.getString(R.styleable.DateItemView_item_result)
        itemTip.text = arr.getString(R.styleable.DateItemView_item_tip)
        val boolean = arr.getBoolean(R.styleable.DateItemView_item_last, false)
        line = findViewById(R.id.split)
        line.visibility = if (boolean) GONE else VISIBLE
        arr.recycle()
    }


    companion object {
        @JvmStatic
        @BindingAdapter("item_result")
        fun setItemResult(view: DateItemView, result: String?) {
            view.itemResult.text = result ?: ""
        }

        @JvmStatic
        @BindingAdapter("item_last")
        fun setItemLast(view: DateItemView, isLast: Boolean?) {
            view.line.visibility = if (isLast == true) GONE else VISIBLE
        }
    }

}