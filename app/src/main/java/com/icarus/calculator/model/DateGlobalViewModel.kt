package com.icarus.calculator.model

import a.icarus.utils.Logger
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class DateGlobalViewModel : ViewModel() {
    var system = MutableLiveData<String>()
    var region = MutableLiveData<String>()
    var standard = MutableLiveData<String>()
    var daylight = MutableLiveData<String>()
    var useDaylight = MutableLiveData<Boolean>()
    var inDaylight = MutableLiveData<Boolean>()
    private var regionId: String
    private val dateFormat = SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.CHINA)
    private val systemFormat = SimpleDateFormat("yyyy年MM月dd日 E HH:mm:ss", Locale.CHINA)

    init {
        system.postValue("")
        region.postValue("选择地区")
        regionId = ""
        standard.postValue("")
        daylight.postValue("")
        useDaylight.postValue(true)
        inDaylight.postValue(false)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    }

    @Synchronized
    fun modifyRegion(id: String?, regionName: String?) {
        if (id.isNullOrEmpty() || regionName.isNullOrEmpty()) return
        region.postValue(regionName)
        regionId = id
    }

    @Synchronized
    fun update() {
        if (regionId.isNotEmpty()) {
            val id = regionId
            val timeZone = TimeZone.getTimeZone(id)
            val use = timeZone.useDaylightTime()
            val inD = timeZone.inDaylightTime(Date())
            val calendar = Calendar.getInstance()
            val calendarStandard = calendar.clone() as Calendar
            calendarStandard.add(Calendar.MILLISECOND, timeZone.rawOffset)
            standard.postValue(
                (if (inD || !use) "" else "当前时间: ") +
                        dateFormat.format(calendarStandard.time)
            )
            calendarStandard.add(Calendar.MILLISECOND, timeZone.dstSavings)
            daylight.postValue(
                (if (inD) "当前时间: " else "") +
                        dateFormat.format(calendarStandard.time)
            )
            useDaylight.postValue(use)
            inDaylight.postValue(inD)
        }
        system.postValue(systemFormat.format(Date()))
    }
}