package com.icarus.calculator.view_model

import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.math.BigInteger
import java.util.*


class ConversionViewModel : ViewModel() {

    var binary = MutableLiveData<String>()
    var octal = MutableLiveData<String>()
    var decimal = MutableLiveData<String>()
    var hexadecimal = MutableLiveData<String>()
    var buttonState = ArrayList<MutableLiveData<Boolean>>()
    var inputType = ArrayList<MutableLiveData<Boolean>>()

    private val inputKeys = listOf(
        "0", "1", "2", "3", "4", "5", "6", "7",
        "8", "9", "A", "B", "C", "D", "E", "F"
    )

    companion object {
        const val BINARY = 2
        const val OCTAL = 8
        const val DECIMAL = 10
        const val HEXADECIMAL = 16
    }

    var select = DECIMAL
        set(value) {
            field = value
            buttonState.forEachIndexed { index, data ->
                data.value = index >= value
            }
            inputType[0].value = value == BINARY
            inputType[1].value = value == OCTAL
            inputType[2].value = value == DECIMAL
            inputType[3].value = value == HEXADECIMAL
        }
    val textWatch = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            s?.run {
                Selection.setSelection(this, length)
            }
        }

    }

    init {
        binary.value = ""
        octal.value = ""
        decimal.value = ""
        hexadecimal.value = ""
        for (i in 1..16) {
            val data = MutableLiveData<Boolean>()
            data.value = i > 10
            buttonState.add(data)
        }
        for (i in 1..4) {
            val data = MutableLiveData<Boolean>()
            data.value = i == 3
            inputType.add(data)
        }
    }


    fun append(index: Int) {
        if (index >= select) {
            return
        }
        val key = inputKeys[index]
        var string: String =
            when (select) {
                BINARY -> binary.value!!
                OCTAL -> octal.value!!
                DECIMAL -> decimal.value!!
                HEXADECIMAL -> hexadecimal.value!!
                else -> ""
            }
        if (string == "0") {
            string = ""
        }
        string += key
        val bigInteger = BigInteger(string, select)
        binary.value = bigInteger.toString(BINARY)
        octal.value = bigInteger.toString(OCTAL)
        decimal.value = bigInteger.toString(DECIMAL)
        hexadecimal.value = bigInteger.toString(HEXADECIMAL).toUpperCase(Locale.ROOT)
    }

    fun delete() {
        var string: String =
            when (select) {
                BINARY -> binary.value!!
                OCTAL -> octal.value!!
                DECIMAL -> decimal.value!!
                HEXADECIMAL -> hexadecimal.value!!
                else -> ""
            }
        if (string.isNotEmpty()) {
            string = string.substring(0, string.length - 1)
        }
        if (string.isEmpty()) {
            clear()
        } else {
            val bigInteger = BigInteger(string, select)
            binary.value = bigInteger.toString(BINARY)
            octal.value = bigInteger.toString(OCTAL)
            decimal.value = bigInteger.toString(DECIMAL)
            hexadecimal.value = bigInteger.toString(HEXADECIMAL)
        }
    }

    fun clear() {
        binary.value = ""
        octal.value = ""
        decimal.value = ""
        hexadecimal.value = ""
    }
}