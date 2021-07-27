package com.icarus.calculator.view_model

import a.icarus.utils.Logger
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
        DatePickerDialog(context).setConfirmListener { calendar, _, _, _, _, _ ->
            when (which) {
                0 -> {
                    startCalendar = calendar
                    start.set(dateFormat.format(calendar.time))
                }
                1 -> {
                    endCalendar = calendar
                    end.set(dateFormat.format(calendar.time))
                }
            }
            calculateInterval()
        }.show()
    }

    private fun calculateInterval() {
        val startCalendar = this.startCalendar
        val endCalendar = this.endCalendar
        if (startCalendar != null && endCalendar != null) {
            Logger.t("Calculate")
        }
    }
}