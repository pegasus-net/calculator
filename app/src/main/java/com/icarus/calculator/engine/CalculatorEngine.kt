package com.icarus.calculator.engine

import a.icarus.utils.Icarus
import com.icarus.calculator.lib.calculator.Calculator
import com.icarus.calculator.lib.calculator.filter.PinyinExprFilter
import java.util.HashMap

interface CalculatorEngine {
    fun calculate(expression: String): String
}

class CalculatorEngineFactory {
    companion object{
        fun getCommonCalculator(): CalculatorEngine {
            return CommonCalculator()
        }
        fun getTextCalculator(): CalculatorEngine {
            val pinyin: HashMap<Char, String> =
                PinyinExprFilter.loadTokens(Icarus.getContext().resources.assets.open("token"))
            return Calculator.createDefault(pinyin)
        }
    }
}