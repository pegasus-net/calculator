package com.icarus.calculator.view_model

import android.content.Context
import androidx.databinding.ObservableField
import com.icarus.calculator.view.DatePickerDialog
import java.text.SimpleDateFormat
import java.util.*

class DateIntervalViewModel {
    var day = ObservableField<String>()
    var month = ObservableField<String>()
    var year = ObservableField<String>()
    var week = ObservableField<String>()
    var hour = ObservableField<String>()
    var minute = ObservableField<String>()
    var workDay = ObservableField<String>()
    var start = ObservableField<String>()
    var startCalendar: Calendar

    var end = ObservableField<String>()
    var endCalendar = Calendar.getInstance()

    private val dateFormat = SimpleDateFormat("yyyy年MM月dd日 E HH:mm", Locale.CHINA)

    init {
        day.set("0天")
        month.set("0月")
        year.set("0年")
        hour.set("0小时")
        minute.set("0分钟")
        week.set("0周")
        workDay.set("0天")
        val instance = Calendar.getInstance()
        startCalendar = instance
        endCalendar = instance
        val date = dateFormat.format(instance.time)
        start.set(date)
        end.set(date)
    }

    fun showDialog(context: Context, which: Int) {
        DatePickerDialog(context).setConfirmListener { calendar, year, month, day, hour, minute ->
            when (which) {
                0 -> {
                    startCalendar = calendar
                    start.set(dateFormat.format(startCalendar.time))
                }
                1 -> {
                    endCalendar = calendar
                    end.set(dateFormat.format(endCalendar.time))
                }
            }
        }.show()
    }
}