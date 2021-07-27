package com.icarus.calculator.engine

import a.icarus.utils.SpUtil
import com.icarus.calculator.annotation.CommonCal
import com.icarus.calculator.util.mainThread
import com.icarus.calculator.util.subThread
import com.icarus.calculator.util.toChinese
import com.icarus.calculator.util.toMoney
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class InputManager @Inject constructor(
    var mediaHelper: MediaHelper,
    @CommonCal var calculator: CalculatorEngine
) {
    enum class KeyType {
        NUMBER_1_9,
        ZERO,
        POINT,
        PI_E,
        OPERATOR,
        FUNCTION,
        SPECIAL_FUNCTION,
        BRACKET_LEFT,
        BRACKET_RIGHT,
        UNKNOWN
    }

    companion object {
        const val PI = "p"
        const val E = "e"

        const val LN = "n"
        const val LG = "g"
        const val SIN = "s"
        const val ARC_SIN = "S"
        const val COS = "c"
        const val ARC_COS = "C"
        const val TAN = "t"
        const val ARC_TAN = "T"

        const val POWER = "^"
        const val SQUARE = "√"
        const val COUNTDOWN = "~"
        const val FACTORIAL = "!"
        const val PERCENT = "%"

        const val SPECIAL = "@"

        const val RAD = "r"
        const val DEG = "d"
        const val INVERSE = "i"
        const val CLEAN = "]"
        const val DELETE = "["
        const val RESULT = "="
    }

    private var inputLock = false
    private var angelType = RAD
    var input = ""
    private var lastInput = ""
    var result = ""
    private var history = ""
    var inputResultsShow: ((String, String) -> Unit)? = null

    fun input(key: String) {
        if (inputLock) return
        val boolean = SpUtil.getBoolean("语音")
        if (boolean) mediaHelper.play(key)
        when (key) {
            INVERSE -> Unit
            RAD -> angelType = RAD
            DEG -> angelType = DEG
            DELETE -> input = input.removeLast()
            CLEAN -> {
                if (input.isNotEmpty() || result.isNotEmpty()) {
                    input = ""
                    if (result.isNotEmpty()) {
                        history += result + "\n"
                        result = ""
                    }
                } else {
                    history = ""
                }
            }
            RESULT -> if (input.isNotEmpty()) {
                inputLock = true
                val dis = trans2Display()
                val exp = trans2Engine() + if (angelType == DEG) '#' else ""
                history += "\n" + dis + "\n"
                input = ""
                subThread {
                    result = calculator.calculate(exp)
                    mainThread {
                        inputLock = false
                        inputResultsShow?.run {
                            this(result, history)
                            if (boolean) mediaHelper.play(result.toChinese())
                        }
                    }
                }
            }

            else -> input = input.add(key)
        }
        lastInput = key
        inputResultsShow?.run {
            if (input.isNotEmpty()) {
                if (result.isNotEmpty()) {
                    history += result + "\n"
                    result = ""
                }
                this(trans2Display(), history)
            } else {
                this(result, history)
            }
        }
    }

    private fun trans2Display(): String {
        var result = ""
        input.forEach {
            val char = it.toString()
            result += when (char) {
                SIN -> "sin("
                COS -> "cos("
                TAN -> "tan("
                ARC_SIN -> "arcsin("
                ARC_COS -> "arccos("
                ARC_TAN -> "arctan("
                LN -> "ln("
                LG -> "lg("
                PI -> "π"
                E -> "e"
                COUNTDOWN -> "^(-1)"
                SPECIAL -> ""
                "*" -> "×"
                "/" -> "÷"
                else -> char
            }
        }
        return result
    }

    private fun trans2Engine(): String {
        var result = ""
        input.forEach {
            val char = it.toString()
            result += when (char) {
                SIN -> "sin("
                COS -> "cos("
                TAN -> "tan("
                ARC_SIN -> "asin("
                ARC_COS -> "acos("
                ARC_TAN -> "atan("
                LN -> "ln("
                LG -> "lg("
                PI -> "π"
                E -> "e"
                COUNTDOWN -> "^(-1)"
                POWER -> "^"
                SQUARE -> "√"
                FACTORIAL -> "!"
                PERCENT -> "/100"
                SPECIAL -> "*"
                else -> char
            }
        }
        val l = result.count { it == '(' }
        val r = result.count { it == ')' }
        if (l > r) {
            for (index in 1..(l - r)) {
                result = "$result)"
            }
        } else if (l < r) {
            for (index in 1..(r - l)) {
                result = "($result"
            }
        }

        return result
    }

    private fun String.removeLast(): String {
        val s = if (isEmpty()) "" else substring(0, length - 1)
        return if (s.endsWith("@")) s.removeLast() else s
    }

    private fun String.add(char: String): String {
        val last = this.getOrNull(length - 1).toString().type()
        val last2 = this.getOrNull(length - 2).toString().type()
        val allowPoint = {
            var s = this
            while (s.isNotEmpty() && (s.last() in '0'..'9')) {
                s = s.substring(0, s.length - 1)
            }
            s.isEmpty() || s.last() != '.'
        }
        val append =
            when (last) {

                KeyType.ZERO -> when (char.type()) {
                    KeyType.ZERO ->
                        if (last2 == KeyType.NUMBER_1_9 || last2 == KeyType.ZERO || last2 == KeyType.POINT)
                            char else ""
                    KeyType.POINT -> if (allowPoint()) char else ""
                    KeyType.NUMBER_1_9 -> char
                    KeyType.PI_E -> "*$char"
                    KeyType.BRACKET_LEFT -> "*$char"
                    KeyType.BRACKET_RIGHT -> char
                    KeyType.FUNCTION -> "@$char"
                    KeyType.SPECIAL_FUNCTION -> char
                    KeyType.OPERATOR -> char
                    KeyType.UNKNOWN -> ""
                }

                KeyType.POINT -> when (char.type()) {
                    KeyType.ZERO -> char
                    KeyType.POINT -> ""
                    KeyType.NUMBER_1_9 -> char
                    KeyType.PI_E -> "*$char"
                    KeyType.BRACKET_LEFT -> "*$char"
                    KeyType.BRACKET_RIGHT -> char
                    KeyType.FUNCTION -> "@$char"
                    KeyType.SPECIAL_FUNCTION -> char
                    KeyType.OPERATOR -> char
                    KeyType.UNKNOWN -> ""
                }

                KeyType.NUMBER_1_9 -> when (char.type()) {
                    KeyType.ZERO -> char
                    KeyType.POINT -> if (allowPoint()) char else ""
                    KeyType.NUMBER_1_9 -> char
                    KeyType.PI_E -> "*$char"
                    KeyType.BRACKET_LEFT -> "*$char"
                    KeyType.BRACKET_RIGHT -> char
                    KeyType.FUNCTION -> "@$char"
                    KeyType.SPECIAL_FUNCTION -> char
                    KeyType.OPERATOR -> char
                    KeyType.UNKNOWN -> ""
                }

                KeyType.PI_E,
                KeyType.BRACKET_RIGHT,
                KeyType.SPECIAL_FUNCTION -> when (char.type()) {
                    KeyType.ZERO -> "*$char"
                    KeyType.POINT -> "*0$char"
                    KeyType.NUMBER_1_9 -> "*$char"
                    KeyType.PI_E -> "*$char"
                    KeyType.BRACKET_LEFT -> "*$char"
                    KeyType.BRACKET_RIGHT -> char
                    KeyType.FUNCTION -> "@$char"
                    KeyType.SPECIAL_FUNCTION -> char
                    KeyType.OPERATOR -> char
                    KeyType.UNKNOWN -> ""
                }

                KeyType.BRACKET_LEFT,
                KeyType.FUNCTION -> when (char.type()) {
                    KeyType.ZERO -> char
                    KeyType.POINT -> "0$char"
                    KeyType.NUMBER_1_9 -> char
                    KeyType.PI_E -> char
                    KeyType.BRACKET_LEFT -> char
                    KeyType.BRACKET_RIGHT -> ""
                    KeyType.FUNCTION -> char
                    KeyType.SPECIAL_FUNCTION -> ""
                    KeyType.OPERATOR -> if (char == "-") char else ""
                    KeyType.UNKNOWN -> ""
                }

                KeyType.OPERATOR -> when (char.type()) {
                    KeyType.ZERO -> char
                    KeyType.POINT -> "0$char"
                    KeyType.NUMBER_1_9 -> char
                    KeyType.PI_E -> char
                    KeyType.BRACKET_LEFT -> char
                    KeyType.BRACKET_RIGHT -> ""
                    KeyType.FUNCTION -> char
                    KeyType.SPECIAL_FUNCTION -> ""
                    KeyType.OPERATOR -> ""
                    KeyType.UNKNOWN -> ""
                }

                KeyType.UNKNOWN -> ""
            }
        return this + append
    }

    private fun String?.type() =
        when (this) {
            "0" -> KeyType.ZERO
            "." -> KeyType.POINT
            "(", "", "null", null -> KeyType.BRACKET_LEFT
            ")" -> KeyType.BRACKET_RIGHT
            "1", "2", "3", "4", "5", "6", "7", "8", "9" -> KeyType.NUMBER_1_9
            "+", "-", "*", "/", POWER -> KeyType.OPERATOR
            PI, E -> KeyType.PI_E
            SIN, COS, TAN, ARC_SIN, ARC_COS, ARC_TAN, LN, LG, SQUARE -> KeyType.FUNCTION
            COUNTDOWN, PERCENT, FACTORIAL -> KeyType.SPECIAL_FUNCTION
            else -> KeyType.UNKNOWN

        }

    fun requestUpper() {
        if (result.isNotEmpty()) {
            inputResultsShow?.run {
                this(result.toMoney(), history)
            }
        }
    }
}