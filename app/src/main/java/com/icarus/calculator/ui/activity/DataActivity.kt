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
import com.icarus.calculator.ui.fragment.DateIntervalFragment
import kotlin.collections.ArrayList

class DataActivity : Activity() {
    private val fragments = ArrayList<Fragment>()
    private val mAdapter = FragmentAdapter(this, fragments)
    private lateinit var pager: ViewPager
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
        indicator = findViewById(R.id.indicator)
    }


    override fun initData() {
        fragments.add(DateIntervalFragment())
        fragments.add(ColorFragment(0xFFFFFF00.toInt()))
        fragments.add(ColorFragment(0xFF00FFFF.toInt()))
        fragments.add(ColorFragment(0xFF0000FF.toInt()))
        pager.adapter = mAdapter


//        val open = resources.assets.open("timezone.json")
//        val json = StreamUtil.toString(open)
//        val type = object : TypeToken<ArrayList<Timezone>>() {}.type
//        val timezones = Gson().fromJson<ArrayList<Timezone>>(json, type)
//        val timeZone = TimeZone.getTimeZone("Asia/Shanghai")
//        timeZone.inDaylightTime(Date()).log()
//        timeZone.useDaylightTime().log()
//
//        val calendar = Calendar.getInstance()
//        val calendarStandard = calendar.clone() as Calendar
//
//        calendarStandard.add(14, timeZone.rawOffset)
//        val simpleDateFormat = SimpleDateFormat("HH:mm:ss z", Locale.CHINA)
//        simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
//        simpleDateFormat.format(calendarStandard.time).log()
//        calendarStandard.add(14, timeZone.dstSavings)
//        simpleDateFormat.format(calendarStandard.time).log()
//        val get = calendar.get(Calendar.HOUR_OF_DAY)
//        calendar.add(Calendar.HOUR_OF_DAY, -15)
//        calendar.get(Calendar.HOUR_OF_DAY).log()
//        Logger.t(get)
//        val now = LocalDate.now()
//        val f = LocalDate.of(2022,8,14)
//        Period.between(now, f).months.log()


        // numberPicker.descendantFocusability = DatePicker.FOCUS_BLOCK_DESCENDANTS
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
    }

    private fun setPagerItem(position: Int) {
        pager.currentItem = position
        indicator.setImageResource(indicatorIcon[position])
    }
}