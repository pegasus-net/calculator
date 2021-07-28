package com.icarus.calculator.ui.fragment

import a.icarus.component.BaseFragment
import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import com.icarus.calculator.R
import com.icarus.calculator.databinding.FragmentDateConversionBinding
import com.icarus.calculator.databinding.FragmentDateNextBinding
import com.icarus.calculator.model.DateConversionViewModel
import com.icarus.calculator.model.DateNextViewModel
import com.icarus.calculator.util.log

class DateNextFragment : BaseFragment() {
    private lateinit var binding: FragmentDateNextBinding
    private var model = DateNextViewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, setLayout(), container, false)
        binding.model = model
        return binding.root
    }

    override fun setLayout(): Int {
        return R.layout.fragment_date_next
    }

    override fun initView(view: View?) {
    }

    override fun initData() {
    }

    override fun initListener() {
        binding.input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s == null || s.isEmpty()) {
                    model.next.set(100000)
                } else {
                    Selection.setSelection(s, s.length)
                    val b = s.toString().startsWith("-")
                    val replace = s.toString().replace(Regex("-"), "")
                    val str: String
                    val value: Int
                    if (replace.isNotEmpty()) {
                        value = replace.toInt()
                        value.log()
                        model.next.set(if (b) 0 - value else value)
                        str = (if (b) "-" else "") + value
                    } else {
                        str = "-"
                        value = 100000
                    }
                    model.next.set(if (b) 0 - value else value)
                    if (str != s.toString()) {
                        binding.input.setText(str)
                    }
                }
            }
        })
        binding.input.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                model.finish()
            }
            false
        }
    }
}