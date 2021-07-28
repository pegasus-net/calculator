package com.icarus.calculator.model

import android.content.Context
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.icarus.calculator.customView.DatePickerDialog
import com.icarus.calculator.engine.Lunar
import java.text.SimpleDateFormat
import java.util.*

class DateNextViewModel {
    var date = ObservableField<String>()
    var result = ObservableField<String>()
    var next = ObservableInt()
    private var calendar: Calendar? = null
    private val dateFormat = SimpleDateFormat("yyyy年MM月dd日 E ", Locale.CHINA)

    init {
        date.set("请选择日期")
        next.set(100000)
        result.set("暂无结果")
    }

    fun showDialog(context: Context) {
        DatePickerDialog(
            context,
            calendar
        ).setConfirmListener { calendar, _, _, _, _, _ ->
            this.calendar = calendar.clone() as Calendar
            date.set(dateFormat.format(calendar.time))
            if (next.get() != 100000) {
                calendar.add(Calendar.DATE, next.get())
                result.set(dateFormat.format(calendar.time))
            } else {
                result.set("暂无结果")
            }
        }.show()
    }

    fun finish() {
        val cal = calendar?.clone() as Calendar?
        cal?.run {
            if (next.get() != 100000) {
                cal.add(Calendar.DATE, next.get())
                result.set(dateFormat.format(cal.time))
            } else {
                result.set("暂无结果")
            }
        }
    }

}