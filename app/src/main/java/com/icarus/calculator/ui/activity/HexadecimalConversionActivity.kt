package com.icarus.calculator.ui.activity

import a.icarus.component.BaseActivity
import a.icarus.utils.WindowUtil
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.icarus.calculator.R
import com.icarus.calculator.databinding.ConversionBinding
import com.icarus.calculator.view_model.ConversionViewModel

class HexadecimalConversionActivity : BaseActivity() {

    private lateinit var binding:ConversionBinding
    private lateinit var model: ConversionViewModel

    override fun initListener() {
    }

    override fun initData() {
    }

    override fun initView() {
        WindowUtil.setBlackStatusText(this)
        binding = DataBindingUtil.setContentView(this, R.layout.acticity_conversion)
        binding.lifecycleOwner = this
        model = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(ConversionViewModel::class.java)
        binding.model = model
        setBackView(R.id.back)
        findViewById<TextView>(R.id.title).text = "进制转换"
        binding.keyboard.post {
            val layoutParams = binding.keyboard.layoutParams as ConstraintLayout.LayoutParams
            val h = binding.keyboard.height
            val w = binding.keyboard.width
            if (w / 5 < h / 4) {
                layoutParams.height = w / 5 * 4
                layoutParams.topToBottom = ConstraintLayout.LayoutParams.UNSET
                binding.keyboard.layoutParams = layoutParams
            }
        }
    }
}