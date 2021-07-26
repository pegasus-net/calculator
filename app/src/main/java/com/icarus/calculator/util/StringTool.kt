package com.icarus.calculator.util

import a.icarus.utils.Logger
import net.sourceforge.pinyin4j.PinyinHelper
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType
import java.util.*

fun String.toChinese() =
    if (startsWith("= ")) {
        val s = substring(2, length)
        if (s.contains('e', true)) "=U"
        else {
            val split = s.split('.')
            when (split.size) {
                1 -> "=" + trans(split[0])
                2 -> "=" + trans(split[0]) + "点" + trans(split[1], false)
                else -> "U"
            }
        }
    } else "U"

fun String.toMoney() =
    if (startsWith("= ")) {
        val number = listOf("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖")
        val s = substring(2, length)
        if (s.contains('e', true)) "金额不再范围内"
        else {
            val split = s.split('.')
            when (split.size) {
                1 -> trans(split[0]).change() + "元整"
                2 -> trans(split[0]).change() + "元" +
                        when (split[1].length) {
                            1 -> trans(split[1], false, number) + "角"
                            else -> trans(split[1][0].toString(), false, number) + "角" +
                                    trans(split[1][1].toString(), false, number) + "分"
                        }
                else -> "U"
            }
        }
    } else "金额不再范围内"

fun String.change(): String {
    return replace("一", "壹")
        .replace("二", "贰")
        .replace("三", "叁")
        .replace("四", "肆")
        .replace("五", "伍")
        .replace("六", "陆")
        .replace("七", "柒")
        .replace("八", "捌")
        .replace("九", "玖")
        .replace("十", "拾")
        .replace("百", "佰")
        .replace("千", "仟")
}

fun trans(
    s: String,
    b: Boolean = true,
    number: List<String> = listOf("零", "一", "二", "三", "四", "五", "六", "七", "八", "九")
): String {
    var str = ""
    val unit = listOf("", "十", "百", "千")
    val unit2 = listOf("", "万", "亿")
    if (b) {
        s.reversed().forEachIndexed { index, c ->
            str = if (c == '-') {
                "负$str"
            } else {
                val value = c.toInt() - 48
                number[value] +
                        (if (value != 0) unit[index % 4] else "") +
                        (if (index % 4 == 0) unit2[index / 4] else "") +
                        str
            }
        }
        if (str != "零") {
            str = str.replace(Regex("零+"), "零")
            str = str.replace(Regex("零万"), "万零")
            str = str.replace(Regex("零亿"), "亿零")
        }
        if (str.startsWith("一十")) {
            str = str.substring(1, str.length)
        }
        if (str.endsWith("零")) {
            str = str.substring(0, str.length - 1)
        }
        return str
    } else {
        s.forEach {
            str += number[it.toInt() - 48]
        }
    }
    return str
}

fun String.toPinyin(): String {
    var pinyin = ""
    val format = HanyuPinyinOutputFormat()
    format.vCharType = HanyuPinyinVCharType.WITH_V
    format.caseType = HanyuPinyinCaseType.LOWERCASE
    format.toneType = HanyuPinyinToneType.WITHOUT_TONE
    forEach {
        val charPinyin = PinyinHelper.toHanyuPinyinStringArray(it, format)
        pinyin += charPinyin?.get(0)?.capitalize(Locale.ROOT) ?: it
    }
    return pinyin
}
