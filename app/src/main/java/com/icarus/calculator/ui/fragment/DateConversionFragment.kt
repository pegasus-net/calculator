package com.icarus.calculator.ui.fragment

import a.icarus.component.BaseFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.icarus.calculator.R
import com.icarus.calculator.databinding.FragmentDateConversionBinding
import com.icarus.calculator.model.DateConversionViewModel
import com.icarus.calculator.model.DateIntervalViewModel

class DateConversionFragment: BaseFragment() {
    private lateinit var binding: FragmentDateConversionBinding
    private var model = DateConversionViewModel()
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
        return R.layout.fragment_date_conversion
    }

    override fun initView(view: View?) {
    }

    override fun initData() {
    }

    override fun initListener() {
    }
}