package com.icarus.calculator.ui.activity

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.icarus.calculator.R
import com.icarus.calculator.adpter.UnitAdapter
import com.icarus.calculator.base.Activity
import com.icarus.calculator.data.UnitInfo
import com.icarus.calculator.util.ExchangeRate
import java.math.BigDecimal

class UnitActivity : Activity() {
    private lateinit var listView: RecyclerView
    private lateinit var mAdapter: UnitAdapter
    private lateinit var inputView: EditText
    private lateinit var inputUnit: UnitInfo
    private lateinit var baseName: TextView
    private lateinit var baseSymbol: TextView
    private lateinit var baseIcon: ImageView
    private lateinit var units: List<UnitInfo>

    override fun setLayout(): Int {
        return R.layout.activity_unit
    }

    override fun initListener() {
        mAdapter.listener = {
            setInputUnit(it)
        }
        inputView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val string = inputView.text.toString()
                val value = if (string.isEmpty()) "100" else string
                mAdapter.baseValue = inputUnit.toBaseUnit(value)
                inputView.isCursorVisible = string.isNotEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    override fun initData() {
        setBackView(R.id.back)
        val names: List<String>
        val symbols: List<String>
        val ratios: List<String>
        val icons: List<Int>?
        val startPosition: Int
        val lastUpdateTime: String?
        when (intent.getIntExtra(TYPE, UNKNOWN)) {
            LENGTH -> {
                setTitle(R.id.title, "长度转换")
                names = listOf(
                    "千米", "米", "分米", "厘米", "毫米", "微米", "纳米", "皮米",
                    "公里", "里", "丈", "尺", "寸", "英里", "海里", "码", "英尺", "英寸"
                )
                symbols = listOf(
                    "km", "m", "dm", "cm", "mm", "μm", "nm", "pm",
                    "公里", "里", "丈", "尺", "寸", "mi", "kt", "yd", "ft", "in"
                )
                ratios = listOf(
                    "1000.0",
                    "1.0",
                    "0.1",
                    "0.01",
                    "0.001",
                    "0.000001",
                    "0.000000001",
                    "0.000000000001",
                    "1000.0",
                    "500.0",
                    (1.0 / 0.3).toString(),
                    (1.0 / 3).toString(),
                    (1.0 / 30).toString(),
                    "1609.344",
                    "1852.0",
                    "0.9144",
                    "0.3048",
                    "0.0254"
                )
                icons = null
                startPosition = 1
                lastUpdateTime = null
            }
            VOLUME -> {
                setTitle(R.id.title, "体积转换")
                names = listOf("立方米", "立方分米", "升", "公石", "立方厘米", "毫升", "立方毫米")
                symbols = listOf("m³", "dm³", "L", "hl", "cm³", "ml", "mm³")
                ratios = listOf(
                    "1",
                    "0.001",
                    "0.001",
                    "0.1",
                    "0.000001",
                    "0.000001",
                    "0.000000001"
                )
                icons = null
                startPosition = 0
                lastUpdateTime = null
            }
            MONEY -> {
                val exchangeRate = ExchangeRate()
                exchangeRate.update()
                setTitle(R.id.title, "汇率")
                names = listOf("CNY", "USD", "GBP", "EUR", "RUB", "JPY", "KRW", "HKD", "MOP", "TWD")
                symbols = listOf("人民币", "美元", "英镑", "欧元", "卢布", "日元", "韩元", "港币", "澳门币", "台币")
                ratios = exchangeRate.get()
                icons = listOf(
                    R.drawable._cny,
                    R.drawable._usd,
                    R.drawable._gbp,
                    R.drawable._eur,
                    R.drawable._rub,
                    R.drawable._jpy,
                    R.drawable._krw,
                    R.drawable._hkd,
                    R.drawable._mop,
                    R.drawable._twd
                )
                startPosition = 0
                lastUpdateTime = exchangeRate.getTime()
            }
            else -> {
                setTitle(R.id.title, "单位转换")
                names = listOf("单位")
                symbols = listOf("unit")
                ratios = listOf("1")
                icons = null
                startPosition = 0
                lastUpdateTime = null
            }
        }
        //解决长按复制粘贴问题
        inputView.isLongClickable = false
        inputView.setTextIsSelectable(false)
        units = createUnits(names, symbols, ratios, icons)
        mAdapter = UnitAdapter(R.layout.item_unit, units)
        listView.adapter = mAdapter
        listView.layoutManager = LinearLayoutManager(this)
        lastUpdateTime?.let {
            findViewById<TextView>(R.id.time).text = it
        }
        setInputUnit(startPosition)
    }

    override fun initView() {
        listView = findViewById(R.id.list)
        inputView = findViewById(R.id.input)
        baseName = findViewById(R.id.base_name)
        baseSymbol = findViewById(R.id.base_symbol)
        baseIcon = findViewById(R.id.icon)
    }

    private fun setInputUnit(position: Int) {
        inputUnit = units[position]
        baseName.text = inputUnit.name
        baseSymbol.text = inputUnit.symbol
        inputView.setText("")
        inputUnit.icon?.run {
            baseIcon.setImageResource(this)
        }
        mAdapter.ignore = position
        mAdapter.baseValue = inputUnit.toBaseUnit("100")
    }

    private fun createUnits(
        names: List<String>,
        symbols: List<String>,
        ratios: List<String>,
        icons: List<Int>? = null
    ): List<UnitInfo> {
        if (names.size != symbols.size || names.size != ratios.size) {
            TODO("数据长度不一致")
        }
        icons?.run {
            if (size != names.size) TODO("数据长度不一致")
        }

        val units = ArrayList<UnitInfo>()
        for (i in names.indices) {
            units.add(UnitInfo(i, names[i], symbols[i], BigDecimal(ratios[i]), icons?.get(i)))
        }
        return units
    }

    companion object {
        private const val TYPE = "unit_type"
        const val LENGTH = 0
        const val VOLUME = 1
        const val MONEY = 2
        const val UNKNOWN = -1
        fun start(activity: android.app.Activity, type: Int) {
            val intent = Intent(activity, UnitActivity::class.java)
            intent.putExtra(TYPE, type)
            activity.startActivity(intent)
        }
    }

}