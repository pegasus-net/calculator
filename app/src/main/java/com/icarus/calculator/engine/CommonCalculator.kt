package com.icarus.calculator.engine

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import java.util.*
import kotlin.math.*

class CommonCalculator : CalculatorEngine {

    private var PI = BigDecimal("3.14159265358979323846")
    private var E = BigDecimal("2.71828182845904523536")
    private var toDegree = 1.0
    override fun calculate(expression: String): String {
        return try {
            var exp = expression
            if (exp.endsWith('#')) {
                toDegree = Math.PI / 180
                exp = exp.substring(0, expression.length - 1)
            }
            val linkedList = getSuffix(splitExp(exp))
            val result = startCalculate(linkedList)
            val d = result.toDouble()
            if (d.isInfinite()) {
                "∞"
            } else if (abs(d) < 0.0000000000001) {
                "= 0"
            } else if (abs(d) > 10000000000) {
                var s = d.toString()
                if (s.endsWith(".0")) {
                    s = s.substring(0, s.length - 2)
                }
                "= $s"
            } else {
                val s = result.setScale(12, RoundingMode.HALF_UP)
                    .stripTrailingZeros()
                    .toPlainString()
                "= $s"
            }
        } catch (e: NumberCalculateException) {
            e.message
        } catch (e: Exception) {
            e.printStackTrace()
            "Error"
        }
    }

    private fun splitExp(exp: String): LinkedList<String> {
        val list: LinkedList<String> = LinkedList()
        val regex = Regex("(?=[+\\-*/()^!√])|(?<=[+\\-*/()^!√])")
        val operateDate = exp.split(regex)
        operateDate.forEach {
            if (it.isNotEmpty()) {
                if (it[it.length - 1] == '.' && "." != it) {
                    //数字 xxx. 转为 xxx。
                    list.add(it.substring(0, it.length - 1))
                } else if (it[0] == '.' && "." != it) {
                    //数字 .xxx 转为 0.xxx。
                    list.add("0$it")
                } else if (it == "-") {
                    //开头为负数或者 （-1）这种情况，在表达式中加0以免发生错误。
                    if (list.isEmpty() || list.last == "(") {
                        list.add("0")
                    }
                    list.add("-")
                } else {
                    if (it != ".") {
                        list.add(it)
                    }
                }
            }
        }

        return list
    }

    private fun getSuffix(infix: LinkedList<String>): LinkedList<String> {
        val listOperator =
            LinkedList<String>()
        val listSuffix = LinkedList<String>()
        infix.forEach {
            //当遇到左括号直接进运算符栈
            if (it == "(") {
                listOperator.add(it)
            } else if (it.isNumber()) {
                listSuffix.add(it)
            } else if (it == ")") {
                var pop = listOperator.removeLast()
                while (pop != "(") {
                    listSuffix.add(pop)
                    pop = listOperator.removeLast()
                }
            } else {
                val level: Int = it.level()
                var lastLevel: Int
                //运算符栈为空或者栈顶为左括号时直接进栈
                //当前运算符等级大于等于栈顶运算符直接进栈
                //小于则循环取出直到取空或大于
                while (!listOperator.isEmpty() && listOperator.last != "(") {
                    lastLevel = listOperator.last.level()
                    if (level > lastLevel) {
                        break
                    } else {
                        listSuffix.add(listOperator.removeLast())
                    }
                }
                listOperator.add(it)
            }
        }
        while (!listOperator.isEmpty()) {
            if (listOperator.last != "(" && listOperator.last != ")") {
                listSuffix.add(listOperator.removeLast())
            }
        }
        return listSuffix
    }

    @Throws(NumberCalculateException::class)
    private fun startCalculate(linkedList: LinkedList<String>): BigDecimal {
        val s = System.currentTimeMillis()
        val numbers = Stack<BigDecimal>()
        var a: BigDecimal
        var b: BigDecimal
        for (str in linkedList) {
            timeCheck(s)
            if (str.isNumber()) {
                val num = when (str) {
                    "e" -> E
                    "π" -> PI
                    else -> BigDecimal(str)
                }
                numbers.push(num)
            } else {
                b = numbers.pop()
                when (str) {
                    "sin" -> numbers.push(BigDecimal(sin(b.toDouble() * toDegree).toString()))
                    "cos" -> numbers.push(BigDecimal(cos(b.toDouble() * toDegree).toString()))
                    "tan" -> numbers.push(BigDecimal(tan(b.toDouble() * toDegree).toString()))
                    "asin" -> numbers.push(BigDecimal(asin(b.toDouble()).toString()))
                    "acos" -> numbers.push(BigDecimal(acos(b.toDouble()).toString()))
                    "atan" -> numbers.push(BigDecimal(atan(b.toDouble()).toString()))
                    "ln" -> numbers.push(BigDecimal(ln(b.toDouble()).toString()))
                    "lg" -> numbers.push(BigDecimal(log10(b.toDouble()).toString()))
                    "√" -> numbers.push(BigDecimal(sqrt(b.toDouble()).toString()))
                    "!" -> try {
                        val i = b.intValueExact()
                        numbers.push(BigDecimal(factorial(i)))
                    } catch (e: ArithmeticException) {
                        throw NumberCalculateException("NaN")
                    }
                    else -> {
                        a = if (numbers.isNotEmpty()) {
                            numbers.pop()
                        } else {
                            throw NumberCalculateException("Error")
                        }
                        when (str) {
                            "+" -> numbers.push(a.add(b))
                            "-" -> numbers.push(a.subtract(b))
                            "*" -> numbers.push(a.multiply(b))
                            "/" -> {
                                val divide = a.divide(b, 20, BigDecimal.ROUND_HALF_UP)
                                numbers.push(divide.stripTrailingZeros())
                            }
                            "^" -> {
                                val pow = a.toDouble().pow(b.toDouble())
                                numbers.push(BigDecimal(pow.toString()))
                            }
                        }
                    }
                }
            }
        }
        if (numbers.size != 1) {
            throw NumberCalculateException("Error")
        }
        return numbers.pop().stripTrailingZeros()
    }


    @Throws(NumberCalculateException::class)
    fun timeCheck(s: Long) {
        if (System.currentTimeMillis() - s > 3000) {
            throw NumberCalculateException("计算超时")
        }
    }

    @Throws(NumberCalculateException::class)
    private fun factorial(number: Int): String {
        if (number < 0) throw NumberCalculateException("NaN")
        if (number > 1000) throw NumberCalculateException("∞")
        var bigInteger = BigInteger("1")
        for (i in 1..number) {
            bigInteger = bigInteger.multiply(BigInteger(i.toString()))
        }
        return bigInteger.toString()
    }

    class NumberCalculateException(override var message: String) : Exception(message)

    private fun String.isNumber() =
        matches(Regex("[0-9]+\\.?[0-9]+"))
                || matches(Regex("[0-9]+"))
                || this == "e" || this == "π"


    private fun String.level() =
        when (this) {
            "+", "-" -> 1
            "*", "/" -> 2
            "sin", "cos", "tan", "asin", "acos", "atan", "ln", "lg", "!", "√", "^" -> 3
            else -> 0
        }
}