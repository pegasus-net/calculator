package com.icarus.calculator.engine

import a.icarus.utils.Logger
import java.time.DayOfWeek
import java.util.*

class DateCalculator(var start: Calendar, var end: Calendar, var reverse: Boolean = false) {
    private val sign = if (reverse) "-" else ""
    private var day: Int
    private var minute: Int
    private var hour: Int
    private var remHour: Int
    private var millisToDay = 1000 * 60 * 60 * 24

    private var year: Int
    private var month: Int
    private var remDay: Int
    private var workDay = 0

    init {
        val interval = ((end.timeInMillis - start.timeInMillis) / 1000).toInt()
        minute = interval / 60
        hour = minute / 60
        remHour = minute % 60
        day = hour / 24
        start.set(Calendar.HOUR, 0)
        start.set(Calendar.MINUTE, 0)
        end.set(Calendar.HOUR, 0)
        end.set(Calendar.MINUTE, 0)
        if (start.get(Calendar.DAY_OF_MONTH) > end.get(Calendar.DAY_OF_MONTH)) {
            val clone = start.clone() as Calendar
            clone.add(Calendar.MONTH, 1)
            clone.set(Calendar.DAY_OF_MONTH, end.get(Calendar.DAY_OF_MONTH))
            remDay = ((clone.timeInMillis - start.timeInMillis) / millisToDay).toInt()
            month = 0
            if (clone.get(Calendar.DAY_OF_MONTH) != end.get(Calendar.DAY_OF_MONTH)) {
                month++
                clone.set(Calendar.DAY_OF_MONTH, end.get(Calendar.DAY_OF_MONTH))
            }
            month += end.get(Calendar.MONTH) - clone.get(Calendar.MONTH)
            year = end.get(Calendar.YEAR) - clone.get(Calendar.YEAR)
            if (month < 0) {
                month += 12
                year -= 1
            }
        } else {
            remDay = end.get(Calendar.DAY_OF_MONTH) - start.get(Calendar.DAY_OF_MONTH)
            month = end.get(Calendar.MONTH) - start.get(Calendar.MONTH)
            year = end.get(Calendar.YEAR) - start.get(Calendar.YEAR)
            if (month < 0) {
                month += 12
                year -= 1
            }
        }

        if (day <= 7) {
            while (start.timeInMillis < end.timeInMillis) {
                if (start.get(Calendar.DAY_OF_WEEK) != 1 && start.get(Calendar.DAY_OF_WEEK) != 7) {
                    workDay++
                }
                start.add(Calendar.DAY_OF_MONTH, 1)
            }
        } else {
            when (start.get(Calendar.DAY_OF_WEEK)) {
                1 -> {
                    workDay += 0
                    start.add(Calendar.DAY_OF_MONTH, 0)
                }
                2 -> {
                    workDay += 5
                    start.add(Calendar.DAY_OF_MONTH, 6)
                }
                3 -> {
                    workDay += 4
                    start.add(Calendar.DAY_OF_MONTH, 5)
                }
                4 -> {
                    workDay += 3
                    start.add(Calendar.DAY_OF_MONTH, 4)
                }
                5 -> {
                    workDay += 2
                    start.add(Calendar.DAY_OF_MONTH, 3)
                }
                6 -> {
                    workDay += 1
                    start.add(Calendar.DAY_OF_MONTH, 2)
                }
                7 -> {
                    workDay += 0
                    start.add(Calendar.DAY_OF_MONTH, 1)
                }
            }
            when (end.get(Calendar.DAY_OF_WEEK)) {
                1 -> {
                    workDay += 5
                    start.add(Calendar.DAY_OF_MONTH, -7)
                }
                2 -> {
                    workDay += 0
                    start.add(Calendar.DAY_OF_MONTH, -1)
                }
                3 -> {
                    workDay += 1
                    start.add(Calendar.DAY_OF_MONTH, -2)
                }
                4 -> {
                    workDay += 2
                    start.add(Calendar.DAY_OF_MONTH, -3)
                }
                5 -> {
                    workDay += 3
                    start.add(Calendar.DAY_OF_MONTH, -4)
                }
                6 -> {
                    workDay += 4
                    start.add(Calendar.DAY_OF_MONTH, -5)
                }
                7 -> {
                    workDay += 5
                    start.add(Calendar.DAY_OF_MONTH, -6)
                }
            }
            workDay += ((end.timeInMillis - start.timeInMillis) / millisToDay / 7 * 5).toInt()
        }
    }

    fun get(filed: Int) =
        when (filed) {
            0 -> "$sign${day}天"
            1 -> "$sign${workDay}天"
            2 -> "$sign${day / 7}周${day % 7}天"
            3 -> "$sign${year * 12 + month}月${remDay}天"
            4 -> "$sign${year}年${month}月${remDay}天"
            5 -> "$sign${hour}小时${remHour}分钟"
            6 -> "$sign${minute}分钟"
            else -> ""
        }
}