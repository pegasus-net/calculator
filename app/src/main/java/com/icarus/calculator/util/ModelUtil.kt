package com.icarus.calculator.util

import a.icarus.utils.SoftInputUtil
import androidx.databinding.BindingAdapter
import com.icarus.calculator.customView.CalculatorInputButton


object ModelUtil {
    @JvmStatic
    @JvmOverloads
    fun copy(text: String, allowEmpty: Boolean = false) {
        if (text.isNotEmpty() || allowEmpty) {
            SoftInputUtil.copy(text)
        }
    }

    @JvmStatic
    @BindingAdapter("inputLock")
    fun lock(button: CalculatorInputButton, isLock: Boolean) {
        if (isLock) {
            button.textColor = 0xFF999999.toInt()
        } else {
            button.textColor = 0xFF333333.toInt()
        }
    }
}