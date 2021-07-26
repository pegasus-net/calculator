package com.icarus.calculator.ui.activity

import a.icarus.impl.ColorFragment
import a.icarus.impl.FragmentAdapter
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.icarus.calculator.R
import com.icarus.calculator.base.Activity
import com.icarus.calculator.ui.fragment.CalculatorFragment
import com.icarus.calculator.ui.fragment.ToolFragment
import com.icarus.calculator.util.dp2px
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView

class MainActivity : Activity() {
    private lateinit var pager: ViewPager
    private lateinit var indicator: MagicIndicator
    private var fragments =
        listOf<Fragment>(
            ColorFragment(0xFF88FF35.toInt()),
            CalculatorFragment(),
            ToolFragment(),
            ColorFragment(0xFF882222.toInt())
        )
    private var titles = listOf("智能计算", "计算器", "工具箱", "我的设置")
    private var icons = listOf(
        R.drawable.pager_icon_0,
        R.drawable.pager_icon_1,
        R.drawable.pager_icon_2,
        R.drawable.pager_icon_3,
        R.drawable.pager_icon_4,
        R.drawable.pager_icon_5,
        R.drawable.pager_icon_6,
        R.drawable.pager_icon_7
    )
    private val colorSelect = 0xFF4078FD.toInt()
    private val colorDeselect = 0xFF384D65.toInt()
    private val mAdapter = FragmentAdapter(this, fragments)
    override fun setLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        pager = findViewById(R.id.view_pager)
        indicator = findViewById(R.id.magic_indicator);

    }

    private var startPosition = 2
    override fun initData() {
        pager.adapter = mAdapter
        pager.offscreenPageLimit = fragments.size - 1

        val commonNavigator = CommonNavigator(this)
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return titles.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val titleView = CommonPagerTitleView(context)
                titleView.setContentView(R.layout.pager_title)
                titleView.setOnClickListener { pager.currentItem = index }
                val icon = titleView.findViewById<ImageView>(R.id.icon)
                val title = titleView.findViewById<TextView>(R.id.title)
                title.text = titles[index]
                titleView.onPagerTitleChangeListener =
                    object : CommonPagerTitleView.OnPagerTitleChangeListener {
                        override fun onDeselected(index: Int, totalCount: Int) {
                            icon.setImageResource(icons[index + totalCount])
                            title.setTextColor(colorDeselect)
                        }

                        override fun onSelected(index: Int, totalCount: Int) {
                            icon.setImageResource(icons[index])
                            title.setTextColor(colorSelect)
                        }

                        override fun onLeave(
                            index: Int,
                            totalCount: Int,
                            leavePercent: Float,
                            leftToRight: Boolean
                        ) {
                        }

                        override fun onEnter(
                            index: Int,
                            totalCount: Int,
                            enterPercent: Float,
                            leftToRight: Boolean
                        ) {
                        }

                    }
                return titleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.lineWidth = dp2px(0F).toFloat()
                indicator.lineHeight = dp2px(0F).toFloat()
                return indicator
            }
        }
        indicator.navigator = commonNavigator
        ViewPagerHelper.bind(indicator, pager)

        pager.currentItem = startPosition
    }

    override fun initListener() {
    }

}