package com.icarus.calculator.data

data class TimeZoneData(
    var chineseName: String,
    var englishName: String,
    var spellName: String
) : Comparable<TimeZoneData> {
    override fun compareTo(other: TimeZoneData): Int {
        return if (spellName > other.spellName) 1 else -1
    }

    fun getFirst(): String {
        return spellName[0].toString()
    }
}
