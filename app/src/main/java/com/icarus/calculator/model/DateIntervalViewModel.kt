package com.icarus.calculator.model

import android.content.Context
import androidx.databinding.ObservableField
import com.icarus.calculator.customView.DatePickerDialog
import com.icarus.calculator.engine.DateCalculator
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
    private var startCalendar: Calendar? = null

    var end = ObservableField<String>()
    private var endCalendar: Calendar? = null

    private val dateFormat = SimpleDateFormat("yyyy年MM月dd日 E HH:mm", Locale.CHINA)

    init {
        day.set("0天")
        month.set("0月0天")
        year.set("0年0月0天")
        hour.set("0小时0分钟")
        minute.set("0分钟")
        week.set("0周0天")
        workDay.set("0天")
        start.set("请选择开始时间")
        end.set("请选择结束时间")
    }

    fun showDialog(context: Context, which: Int) {

        when (which) {
            0 -> {
                DatePickerDialog(
                    context,
                    startCalendar
                ).setConfirmListener { calendar, _, _, _, _, _ ->
                    startCalendar = calendar
                    start.set(dateFormat.format(calendar.time))
                    calculateInterval()
                }.show()
            }
            1 -> {
                DatePickerDialog(
                    context,
                    endCalendar
                ).setConfirmListener { calendar, _, _, _, _, _ ->
                    endCalendar = calendar
                    end.set(dateFormat.format(calendar.time))
                    calculateInterval()
                }.show()
            }
        }

    }

    private fun calculateInterval() {
        val startCalendar = this.startCalendar?.clone() as Calendar?
        val endCalendar = this.endCalendar?.clone() as Calendar?
        if (startCalendar != null && endCalendar != null) {
            val calculator =
                if (startCalendar.timeInMillis <= endCalendar.timeInMillis) {
                    DateCalculator(startCalendar, endCalendar)
                } else {
                    DateCalculator(endCalendar, startCalendar, true)
                }
            day.set(calculator.get(0))
            workDay.set(calculator.get(1))
            week.set(calculator.get(2))
            month.set(calculator.get(3))
            year.set(calculator.get(4))
            hour.set(calculator.get(5))
            minute.set(calculator.get(6))
        }
    }
}