package com.icarus.calculator.ui.activity

import a.icarus.utils.Logger
import a.icarus.utils.StreamUtil
import android.os.Build
import android.widget.DatePicker
import android.widget.NumberPicker
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.icarus.calculator.R
import com.icarus.calculator.base.Activity
import com.icarus.calculator.data.Timezone
import com.icarus.calculator.engine.CalculatorEngineFactory
import com.icarus.calculator.lib.calculator.Calculator
import com.icarus.calculator.lib.calculator.filter.PinyinExprFilter
import com.icarus.calculator.util.log
import com.icarus.calculator.util.sp2px
import com.icarus.calculator.view.DatePickerDialog
import java.text.SimpleDateFormat
import java.time.*
import java.util.*

class DataActivity : Activity() {
    override fun setLayout(): Int {
        return R.layout.activity_data
    }

    override fun initView() {

    }


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun initData() {
//        val open = resources.assets.open("timezone.json")
//        val json = StreamUtil.toString(open)
//        val type = object : TypeToken<ArrayList<Timezone>>() {}.type
//        val timezones = Gson().fromJson<ArrayList<Timezone>>(json, type)
//        val timeZone = TimeZone.getTimeZone("Asia/Shanghai")
//        timeZone.inDaylightTime(Date()).log()
//        timeZone.useDaylightTime().log()
//
//        val calendar = Calendar.getInstance()
//        val calendarStandard = calendar.clone() as Calendar
//
//        calendarStandard.add(14, timeZone.rawOffset)
//        val simpleDateFormat = SimpleDateFormat("HH:mm:ss z", Locale.CHINA)
//        simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
//        simpleDateFormat.format(calendarStandard.time).log()
//        calendarStandard.add(14, timeZone.dstSavings)
//        simpleDateFormat.format(calendarStandard.time).log()
//        val get = calendar.get(Calendar.HOUR_OF_DAY)
//        calendar.add(Calendar.HOUR_OF_DAY, -15)
//        calendar.get(Calendar.HOUR_OF_DAY).log()
//        Logger.t(get)
//        val now = LocalDate.now()
//        val f = LocalDate.of(2022,8,14)
//        Period.between(now, f).months.log()

        DatePickerDialog(this,false).setConfirmListener { cal,year, month, day, hour, minute ->
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
            dateFormat.format(cal.time).log()
        }.show()
       // numberPicker.descendantFocusability = DatePicker.FOCUS_BLOCK_DESCENDANTS
    }

    override fun initListener() {

    }
}