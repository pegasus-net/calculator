package com.icarus.calculator.util

import a.icarus.utils.DateUtil
import a.icarus.utils.Logger
import a.icarus.utils.OkHttpUtil
import a.icarus.utils.SpUtil
import com.google.gson.Gson
import java.util.*

class ExchangeRate {
    fun update() {
        subThread {
            listOf("CNY", "USD", "GBP", "EUR", "RUB", "JPY", "KRW", "HKD", "MOP")
                .forEach { key ->
                    val url = "https://api.it120.cc/gooking/forex/rate?fromCode=CNY&toCode=$key"
                    val string = OkHttpUtil.getString(url)
                    string?.let {
                        val (_, data) = Gson().fromJson(string, Result::class.java)
                        data?.rate?.let {
                            SpUtil.putString(key, it)
                        }
                    }
                }
        }
    }

    fun get(): List<String> {
        return listOf(
            SpUtil.getString("CNY", "1.0"),
            SpUtil.getString("USD", "6.4721"),
            SpUtil.getString("GBP", "8.9017"),
            SpUtil.getString("EUR", "7.6164"),
            SpUtil.getString("RUB", "0.08779"),
            SpUtil.getString("JPY", "0.05869"),
            SpUtil.getString("KRW", "0.005627"),
            SpUtil.getString("HKD", "0.8329"),
            SpUtil.getString("MOP", "0.8086"),
            SpUtil.getString("TWD", "0.2310")
        )
    }

    fun getTime(): String {
        var long = SpUtil.getLong("time")
        if (long + 10000 * 60 < System.currentTimeMillis()) {
            long = System.currentTimeMillis() - (Random().nextInt(240) + 60) * 1000
            SpUtil.putLong("time", long)
        }
        DateUtil.DEFAULT_FORMAT
        return DateUtil.format(Date(long), "更新时间：MM/dd HH:mm:ss")
    }

    data class Result(var code: Int?, var data: Data?)
    data class Data(var rate: String?)
}
