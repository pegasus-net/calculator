package com.icarus.calculator.base

import a.icarus.component.BaseActivity
import a.icarus.utils.WindowUtil
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes

abstract class Activity : BaseActivity() {

    override fun initTheme() {
        super.initTheme()
        WindowUtil.setBlackStatusText(this)
        setContentView(setLayout())
    }

    @LayoutRes
    abstract fun setLayout(): Int
    fun setTitle(id: Int, title: String) {
        findViewById<TextView>(id).text = title
    }
}