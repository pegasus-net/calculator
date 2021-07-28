package com.icarus.calculator.ui.fragment

import a.icarus.component.BaseFragment
import a.icarus.utils.SoftInputUtil
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.icarus.calculator.R
import com.icarus.calculator.engine.InputManager
import com.icarus.calculator.customView.CalculatorInputButton
import com.icarus.calculator.customView.CalculatorSetButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CalculatorScienceFragment : BaseFragment() {
    private lateinit var keyboard: ConstraintLayout
    private lateinit var history: TextView
    private lateinit var current: TextView
    private lateinit var scroll: ScrollView
    private lateinit var scrollRoot: ConstraintLayout
    private val set = ArrayList<CalculatorSetButton>()
    private val keys = ArrayList<CalculatorInputButton>()
    private val tri = ArrayList<CalculatorInputButton>()

    @Inject
    lateinit var inputManager: InputManager

    override fun initData() {
        val layoutParams = keyboard.layoutParams
        val screenWidth = mContext.resources.displayMetrics.widthPixels
        val paddingW = keyboard.paddingLeft + keyboard.paddingRight
        val paddingH = keyboard.paddingTop + keyboard.paddingBottom
        layoutParams.height = (screenWidth - paddingW) * 4 / 5 + paddingH

        inputManager.inputResultsShow = { a, b ->
            current.text = a
            if (b.isEmpty()) {
                history.visibility = View.GONE
            } else {
                history.visibility = View.VISIBLE
                history.text = b
            }
            scroll.postDelayed({ scroll.scrollTo(0, scrollRoot.height) }, 20)
        }
        lifecycle.addObserver(inputManager.mediaHelper)
    }

    override fun setLayout(): Int {
        return R.layout.fragment_calculator_science
    }

    override fun initView(view: View?) {

        keyboard = findViewById(R.id.calculator_keyboard)
        history = findViewById(R.id.history_input)
        current = findViewById(R.id.current_input)
        scroll = findViewById(R.id.scroll)
        scrollRoot = findViewById(R.id.scroll_root)

        set.clear()
        set.add(findViewById(R.id.history))
        set.add(findViewById(R.id.capital))
        set.add(findViewById(R.id.vibrate))
        set.add(findViewById(R.id.voice))
        set.add(findViewById(R.id.copy))

        keys.clear()
        tri.clear()
        keys.add(findViewById(R.id.number_0))
        keys.add(findViewById(R.id.number_1))
        keys.add(findViewById(R.id.number_2))
        keys.add(findViewById(R.id.number_3))
        keys.add(findViewById(R.id.number_4))
        keys.add(findViewById(R.id.number_5))
        keys.add(findViewById(R.id.number_6))
        keys.add(findViewById(R.id.number_7))
        keys.add(findViewById(R.id.number_8))
        keys.add(findViewById(R.id.number_9))
        keys.add(findViewById(R.id.number_point))
        keys.add(findViewById(R.id.number_pi))
        keys.add(findViewById(R.id.number_e))
        keys.add(findViewById(R.id.operator_add))
        keys.add(findViewById(R.id.operator_sub))
        keys.add(findViewById(R.id.operator_mulit))
        keys.add(findViewById(R.id.operator_div))
        keys.add(findViewById(R.id.operator_equals))
        keys.add(findViewById(R.id.special_left))
        keys.add(findViewById(R.id.special_right))
        keys.add(findViewById(R.id.input_clean))
        keys.add(findViewById(R.id.input_delete))
        keys.add(findViewById(R.id.input_inverse))
        keys.add(findViewById(R.id.input_angle))
        tri.add(keys.last())
        keys.add(findViewById(R.id.function_sin))
        tri.add(keys.last())
        keys.add(findViewById(R.id.function_cos))
        tri.add(keys.last())
        keys.add(findViewById(R.id.function_tan))
        tri.add(keys.last())
        keys.add(findViewById(R.id.function_lg))
        keys.add(findViewById(R.id.function_ln))
        keys.add(findViewById(R.id.function_power))
        keys.add(findViewById(R.id.function_square))
        keys.add(findViewById(R.id.function_down))
        keys.add(findViewById(R.id.function_factorial))
        keys.add(findViewById(R.id.function_percent))

    }

    override fun initListener() {
        set[1].setOnClickListener {
            inputManager.requestUpper()
        }
        set[4].setOnClickListener {
            val text = current.text.toString()
            if (text.trim().isNotEmpty()) {
                if (text.startsWith("= ")) {
                    SoftInputUtil.copy(text.substring(2, text.length).trim(), true)
                } else {
                    SoftInputUtil.copy(text.trim(), true)
                }
            }
        }
        keys.forEach { key ->
            key.vibratorSet = set[2]
            key.setOnClickListener {
                it as CalculatorInputButton
                when (it.input) {
                    InputManager.INVERSE -> {
                        val b = tri[1].input == InputManager.SIN
                        tri[1].input = if (b) InputManager.ARC_SIN else InputManager.SIN
                        tri[1].show = if (b) "arcsin" else "sin"
                        tri[2].input = if (b) InputManager.ARC_COS else InputManager.COS
                        tri[2].show = if (b) "arccos" else "cos"
                        tri[3].input = if (b) InputManager.ARC_TAN else InputManager.TAN
                        tri[3].show = if (b) "arctan" else "tan"
                    }
                    InputManager.RAD -> {
                        tri[0].show = "deg"
                        tri[0].input = InputManager.DEG
                    }
                    InputManager.DEG -> {
                        tri[0].show = "rad"
                        tri[0].input = InputManager.RAD
                    }
                }
                inputManager.input(it.input)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        set.forEach {
            it.onResume()
        }
    }
}