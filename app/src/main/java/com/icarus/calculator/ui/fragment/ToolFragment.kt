package com.icarus.calculator.ui.fragment

import a.icarus.component.BaseFragment
import android.view.View
import android.widget.ImageView
import com.icarus.calculator.R
import com.icarus.calculator.ui.activity.DateActivity
import com.icarus.calculator.ui.activity.HexadecimalConversionActivity
import com.icarus.calculator.ui.activity.UnitActivity
import com.icarus.calculator.util.startActivity

class ToolFragment : BaseFragment() {
    override fun initListener() {

    }

    override fun initData() {
        findViewById<ImageView>(R.id.tool_1).setOnClickListener {
            startActivity(HexadecimalConversionActivity::class.java)
        }
        findViewById<ImageView>(R.id.tool_2).setOnClickListener {
            UnitActivity.start(mActivity, UnitActivity.LENGTH)
        }
        findViewById<ImageView>(R.id.tool_3).setOnClickListener {
            UnitActivity.start(mActivity, UnitActivity.VOLUME)
        }
        findViewById<ImageView>(R.id.tool_4).setOnClickListener {
            startActivity(DateActivity::class.java)
        }
        findViewById<ImageView>(R.id.tool_5).setOnClickListener {
            UnitActivity.start(mActivity, UnitActivity.MONEY)
        }
    }

    override fun setLayout(): Int {
        return R.layout.fragment_tool
    }

    override fun initView(view: View?) {

    }

}