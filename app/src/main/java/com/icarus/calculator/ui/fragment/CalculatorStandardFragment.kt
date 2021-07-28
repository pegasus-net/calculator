package com.icarus.calculator.ui.fragment

import a.icarus.component.BaseFragment
import a.icarus.utils.SoftInputUtil
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.icarus.calculator.engine.InputManager
import com.icarus.calculator.R
import com.icarus.calculator.customView.CalculatorInputButton
import com.icarus.calculator.customView.CalculatorSetButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CalculatorStandardFragment : BaseFragment() {
    private lateinit var keyboard: ConstraintLayout
    private lateinit var history: TextView
    private lateinit var current: TextView
    private lateinit var scroll: ScrollView
    private val set = ArrayList<CalculatorSetButton>()
    private val key = ArrayList<CalculatorInputButton>()

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
            scroll.postDelayed({ scroll.scrollTo(0, Int.MAX_VALUE / 2) }, 20)
        }

        lifecycle.addObserver(inputManager.mediaHelper)
    }

    override fun setLayout(): Int {
        return R.layout.fragment_calculator_standard
    }

    override fun initView(view: View?) {
        set.clear()
        set.add(findViewById(R.id.history))
        set.add(findViewById(R.id.capital))
        set.add(findViewById(R.id.vibrate))
        set.add(findViewById(R.id.voice))
        set.add(findViewById(R.id.copy))
        key.clear()
        key.add(findViewById(R.id.number_0))
        key.add(findViewById(R.id.number_1))
        key.add(findViewById(R.id.number_2))
        key.add(findViewById(R.id.number_3))
        key.add(findViewById(R.id.number_4))
        key.add(findViewById(R.id.number_5))
        key.add(findViewById(R.id.number_6))
        key.add(findViewById(R.id.number_7))
        key.add(findViewById(R.id.number_8))
        key.add(findViewById(R.id.number_9))
        key.add(findViewById(R.id.number_point))
        key.add(findViewById(R.id.operator_add))
        key.add(findViewById(R.id.operator_sub))
        key.add(findViewById(R.id.operator_mulit))
        key.add(findViewById(R.id.operator_div))
        key.add(findViewById(R.id.operator_equals))
        key.add(findViewById(R.id.input_clean))
        key.add(findViewById(R.id.input_delete))

        keyboard = findViewById(R.id.calculator_keyboard)
        history = findViewById(R.id.history_input)
        current = findViewById(R.id.current_input)
        scroll = findViewById(R.id.scroll)
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
        key.forEach {
            it.vibratorSet = set[2]
            val key = it.input
            it.setOnClickListener {
                inputManager.input(key)
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