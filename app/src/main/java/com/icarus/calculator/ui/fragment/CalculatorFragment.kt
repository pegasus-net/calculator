package com.icarus.calculator.ui.fragment

import a.icarus.component.BaseFragment
import a.icarus.impl.FragmentAdapter
import a.icarus.utils.Logger
import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.icarus.calculator.R
import dagger.hilt.android.AndroidEntryPoint

class CalculatorFragment : BaseFragment() {

    lateinit var pager: ViewPager
    lateinit var indicator: ImageView
    private val fragments =
        listOf(CalculatorStandardFragment(), CalculatorScienceFragment())
    private val img = listOf(
        R.drawable.pager_easy,
        R.drawable.pager_since
    )
    lateinit var mAdapter: FragmentAdapter

    @SuppressLint("ClickableViewAccessibility")
    override fun initListener() {
        indicator.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    Logger.t(event.x)
                    if (event.x > v.width / 2) {
                        pager.setCurrentItem(1,false)
                    } else {
                        pager.setCurrentItem(0,false)
                    }
                }
            }
            true
        }
        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                indicator.setImageResource(img[position])
            }

        })
    }

    override fun initData() {
        mAdapter = FragmentAdapter(mActivity, fragments)
        pager.adapter = mAdapter
    }

    override fun setLayout(): Int {
        return R.layout.fragment_calculator
    }

    override fun initView(view: View?) {
        pager = findViewById(R.id.pager)
        indicator = findViewById(R.id.indicator)
    }

}