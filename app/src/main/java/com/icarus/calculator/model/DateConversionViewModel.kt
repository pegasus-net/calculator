package com.icarus.calculator.model

import android.content.Context
import androidx.databinding.ObservableField
import com.icarus.calculator.customView.DatePickerDialog
import com.icarus.calculator.engine.Lunar
import java.text.SimpleDateFormat
import java.util.*

class DateConversionViewModel {
    var date = ObservableField<String>()
    var lunar = ObservableField<String>()
    private var calendar: Calendar? = null
    private val dateFormat = SimpleDateFormat("yyyy年MM月dd日 E ", Locale.CHINA)

    init {
        date.set("请选择日期")
    }

    fun showDialog(context: Context) {
        DatePickerDialog(
            context,
            calendar
        ).setConfirmListener { calendar, year, month, day, _, _ ->
            this.calendar = calendar
            date.set(dateFormat.format(calendar.time))
            lunar.set(Lunar.getDate(year, month, day))
        }.show()

    }
}