package com.icarus.calculator.ui.activity

import a.icarus.utils.StreamUtil
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ListView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.icarus.calculator.R
import com.icarus.calculator.adpter.RegionAdapter
import com.icarus.calculator.base.Activity
import com.icarus.calculator.data.TimeZoneData
import com.icarus.calculator.util.mainThread
import com.icarus.calculator.util.subThread
import java.io.File
import java.io.FileOutputStream
import kotlin.collections.ArrayList

class RegionActivity : Activity() {
    private lateinit var timezones: ArrayList<TimeZoneData>
    private lateinit var input: EditText
    private lateinit var list: ListView
    private lateinit var mAdapter: RegionAdapter
    override fun setLayout(): Int {
        return R.layout.activity_region
    }

    override fun initView() {
        setBackView(R.id.back)
        setTitle(R.id.title, "选取城市")
        input = findViewById(R.id.input)
        list = findViewById(R.id.list)
    }

    override fun initData() {
        subThread {
            val type = object : TypeToken<ArrayList<TimeZoneData>>() {}.type
            val open = assets.open("timezone.json")
            val json = StreamUtil.toString(open)
            timezones = Gson().fromJson(json, type)
            timezones.sort()
            mAdapter.getList().addAll(timezones)
            mainThread {
                mAdapter.notifyDataSetChanged()
            }
        }
        mAdapter = RegionAdapter(ArrayList())
        list.adapter = mAdapter

    }
    
    override fun initListener() {
        list.setOnItemClickListener { _, _, position, _ ->
            val timezone = mAdapter.getList()[position]
            val intent = Intent()
            intent.putExtra("id", timezone.englishName)
            intent.putExtra("name", timezone.chineseName)
            setResult(RESULT_OK, intent)
            finish()
        }
        input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val str = s.toString().replace(" ", "")
                val filterList = ArrayList<TimeZoneData>()
                timezones.forEach {
                    if (it.chineseName.contains(str)) {
                        filterList.add(it)
                    }
                }
                mAdapter.getList().clear()
                mAdapter.getList().addAll(filterList)
                mAdapter.notifyDataSetChanged()
            }

        })
    }
}