package com.icarus.calculator.data

import a.icarus.utils.Strings
import java.math.BigDecimal

data class UnitInfo(
    val position: Int,
    val name: String,
    val symbol: String,
    val ratio: BigDecimal,
    val icon: Int?
) : Comparable<UnitInfo> {
    override fun compareTo(other: UnitInfo): Int {
        return if (position > other.position) 1 else -1
    }

    fun toBaseUnit(value: String): String {
        val result = BigDecimal(value).multiply(ratio)
        return result.toPlainString()
    }

    fun fromBaseUnit(value: String): String {
        val result = BigDecimal(value).divide(ratio, 20, BigDecimal.ROUND_HALF_UP)
        if (result.toDouble() == 0.0) {
            return "0"
        }
        if (result.toDouble() >= 1000000000) {
            return Strings.format("%.8E", result.toDouble())
                .replace(Regex("0+E"), "E")
                .replace(Regex("\\.E"), ".0E")

        }
        if (result.toDouble() <= 0.0001) {
            return Strings.format("%.8E", result.toDouble())
                .replace(Regex("0+E"), "E")
                .replace(Regex("\\.E"), ".0E")
        }
        return result.setScale(8, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString()
    }
}