package com.icarus.calculator.ui.activity

import a.icarus.impl.ColorFragment
import a.icarus.impl.FragmentAdapter
import android.annotation.SuppressLint
import android.view.MotionEvent
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.icarus.calculator.R
import com.icarus.calculator.base.Activity
import com.icarus.calculator.customView.NoScrollViewPager
import com.icarus.calculator.ui.fragment.DateConversionFragment
import com.icarus.calculator.ui.fragment.DateGlobalFragment
import com.icarus.calculator.ui.fragment.DateIntervalFragment
import com.icarus.calculator.ui.fragment.DateNextFragment
import kotlin.collections.ArrayList

class DateActivity : Activity() {
    private val fragments = ArrayList<Fragment>()
    private val mAdapter = FragmentAdapter(this, fragments)
    private lateinit var pager: NoScrollViewPager
    private lateinit var indicator: ImageView
    private val indicatorIcon =
        listOf(R.drawable.date_0, R.drawable.date_1, R.drawable.date_2, R.drawable.date_3)

    override fun setLayout(): Int {
        return R.layout.activity_data
    }

    override fun initView() {
        setBackView(R.id.back)
        setTitle(R.id.title, "日期计算")
        pager = findViewById(R.id.pager)
        pager.forbidScroll = false
        indicator = findViewById(R.id.indicator)
    }


    override fun initData() {
        fragments.add(DateIntervalFragment())
        fragments.add(DateConversionFragment())
        fragments.add(DateNextFragment())
        fragments.add(DateGlobalFragment())
        pager.adapter = mAdapter
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initListener() {
        indicator.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    val w = v.width
                    if (event.y > 0 && event.y < v.height) {
                        when (event.x.toInt()) {
                            in 0..w / 4 -> setPagerItem(0)
                            in w / 4..w / 2 -> setPagerItem(1)
                            in w / 2..w / 4 * 3 -> setPagerItem(2)
                            in w / 4 * 3..w -> setPagerItem(3)
                        }
                    }
                }
            }
            true
        }
        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                setPagerItem(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    private fun setPagerItem(position: Int) {
        pager.currentItem = position
        indicator.setImageResource(indicatorIcon[position])
    }

}