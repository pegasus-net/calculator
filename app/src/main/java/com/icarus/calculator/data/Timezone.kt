package com.icarus.calculator.data

import a.icarus.utils.Logger
import com.icarus.calculator.util.toPinyin

data class Timezone(val name: String, val id: String) : Comparable<Timezone> {
    fun getEnName() = name.toPinyin()
    override fun compareTo(other: Timezone): Int {
        return this.getEnName().compareTo(other.getEnName())
    }
}