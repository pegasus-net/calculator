package com.icarus.calculator.customView

import a.icarus.component.BaseDialog
import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.DatePicker
import android.widget.NumberPicker
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.icarus.calculator.R
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("SetTextI18n")
class DatePickerDialog(
    context: Context,
    var cal: Calendar? = null,
    var timeEnable: Boolean = true
) :
    BaseDialog(context, R.layout.dialog_date_picker) {

    private val numberPickers = ArrayList<NumberPicker>()
    private val numbers = ArrayList<Int>()
    private val min = listOf(1970, 1, 1, 0, 0)
    private val max = mutableListOf(2050, 12, 31, 23, 59)
    private val date: TextView
    private val time: TextView
    private val colorBlack = 0xFF333333.toInt()
    private val colorGray = 0xFF999999.toInt()
    private val dateContainer: ConstraintLayout
    private val timeContainer: ConstraintLayout
    private var listener: ((calendar: Calendar, year: Int, month: Int, day: Int, hour: Int, minute: Int) -> Unit)? =
        null

    init {
        numberPickers.add(findViewById(R.id.year))
        numberPickers.add(findViewById(R.id.month))
        numberPickers.add(findViewById(R.id.day))
        numberPickers.add(findViewById(R.id.hour))
        numberPickers.add(findViewById(R.id.minute))
        dateContainer = findViewById(R.id.date_container)
        timeContainer = findViewById(R.id.time_container)

        val calendar = cal ?: Calendar.getInstance()
        max[2] = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        numbers.add(calendar.get(Calendar.YEAR))
        numbers.add(calendar.get(Calendar.MONTH) + 1)
        numbers.add(calendar.get(Calendar.DAY_OF_MONTH))
        numbers.add(calendar.get(Calendar.HOUR_OF_DAY))
        numbers.add(calendar.get(Calendar.MINUTE))
        numbers.add(calendar.get(Calendar.DAY_OF_WEEK))

        date = findViewById(R.id.date)
        time = findViewById(R.id.time)
        date.text = numbers[1].format() + "月" + numbers[2].format() + "日" + numbers[5].toWeek()
        time.text = numbers[3].format() + ":" + numbers[4].format()

        numberPickers.forEachIndexed { index, it ->
            it.descendantFocusability = DatePicker.FOCUS_BLOCK_DESCENDANTS
            it.wrapSelectorWheel = false
            it.minValue = min[index]
            it.maxValue = max[index]
            it.setFormatter {
                it.format()
            }
            it.value = numbers[index]
            it.setOnValueChangedListener { _, _, value ->
                numbers[index] = value
                if (index < 3) {
                    val instance = Calendar.getInstance()
                    instance.set(numbers[0], numbers[1] - 1, 1)
                    if (max[2] != instance.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                        max[2] = instance.getActualMaximum(Calendar.DAY_OF_MONTH)
                        numberPickers[2].maxValue = max[2]
                        numbers[2] = numberPickers[2].value

                    }
                    instance.set(Calendar.DAY_OF_MONTH, numbers[2])
                    numbers[5] = instance.get(Calendar.DAY_OF_WEEK)
                    date.text =
                        numbers[1].format() + "月" + numbers[2].format() + "日" + numbers[5].toWeek()
                } else {
                    time.text = numbers[3].format() + ":" + numbers[4].format()
                }
            }
        }

        date.setOnClickListener {
            selectDate()
        }
        time.setOnClickListener {
            selectTime()
        }
        setViewOnClickListener(R.id.cancel) {
            dismiss()
        }
        setViewOnClickListener(R.id.confirm) {
            listener?.run {
                val instance = Calendar.getInstance()
                instance.set(numbers[0], numbers[1] - 1, numbers[2], numbers[3], numbers[4], 0)
                instance.set(Calendar.MILLISECOND, 0)
                this(instance, numbers[0], numbers[1], numbers[2], numbers[3], numbers[4])
            }
            dismiss()
        }
        selectDate()
    }

    private fun selectTime() {
        if (!timeEnable) return
        dateContainer.visibility = View.GONE
        date.setTextColor(colorGray)
        timeContainer.visibility = View.VISIBLE
        time.setTextColor(colorBlack)
    }

    private fun selectDate() {
        dateContainer.visibility = View.VISIBLE
        date.setTextColor(colorBlack)
        timeContainer.visibility = View.GONE
        time.setTextColor(colorGray)
    }

    private fun Int.format(): String {
        return if (this < 10) "0$this" else "$this"
    }

    private fun Int.toWeek(): String {
        val weeks = listOf("周一", "周二", "周三", "周四", "周五", "周六", "周日")
        return when (this) {
            1 -> weeks[6]
            2 -> weeks[0]
            3 -> weeks[1]
            4 -> weeks[2]
            5 -> weeks[3]
            6 -> weeks[4]
            7 -> weeks[5]
            else -> "未知"
        }
    }

    fun setConfirmListener(listener: (calendar: Calendar, year: Int, month: Int, day: Int, hour: Int, minute: Int) -> Unit): DatePickerDialog {
        this.listener = listener
        return this
    }
}