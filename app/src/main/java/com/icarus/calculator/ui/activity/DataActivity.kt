package com.icarus.calculator.ui.activity

import a.icarus.utils.Logger
import com.icarus.calculator.R
import com.icarus.calculator.base.Activity
import com.icarus.calculator.engine.CalculatorEngineFactory
import com.icarus.calculator.lib.calculator.Calculator
import com.icarus.calculator.lib.calculator.filter.PinyinExprFilter
import java.util.*

class DataActivity : Activity() {
    override fun setLayout(): Int {
        return R.layout.activity_data
    }

    override fun initView() {

    }

    override fun initData() {
//        val open = resources.assets.open("timezone.json")
//        val json = StreamUtil.toString(open)
//        val type = object : TypeToken<ArrayList<Timezone>>() {}.type
//        val timezones = Gson().fromJson<ArrayList<Timezone>>(json, type)
//        timezones.sort()
        val calculator = CalculatorEngineFactory.getTextCalculator()
        val eval = calculator.calculate("正弦0")
        Logger.t(eval)
    }

    override fun initListener() {

    }
}