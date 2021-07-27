package com.icarus.calculator.ui.fragment

import a.icarus.component.BaseFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.icarus.calculator.R
import com.icarus.calculator.databinding.FragmentDateIntervalBinding
import com.icarus.calculator.view_model.DateIntervalViewModel

class DateIntervalFragment : BaseFragment() {
    private lateinit var binding: FragmentDateIntervalBinding
    private var model = DateIntervalViewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, setLayout(), container, false)
        return binding.root
    }

    override fun setLayout(): Int {
        return R.layout.fragment_date_interval
    }

    override fun initView(view: View?) {
    }

    override fun initData() {
        binding.model = model
    }

    override fun initListener() {
    }
}