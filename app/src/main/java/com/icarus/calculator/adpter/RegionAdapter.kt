package com.icarus.calculator.adpter

import a.icarus.impl.ListAdapter
import android.view.View
import android.widget.TextView
import com.icarus.calculator.R
import com.icarus.calculator.data.TimeZoneData

class RegionAdapter(list: List<TimeZoneData>) :
    ListAdapter<TimeZoneData, RegionAdapter.ViewHolder>(list, R.layout.item_region) {


    class ViewHolder(view: View) : ListAdapter.ViewHolder(view) {
        var capital: TextView = findViewById(R.id.capital)
        var title: TextView = findViewById(R.id.title)
    }

    override fun onCreateViewHolder(convertView: View?): ViewHolder {
        return ViewHolder(convertView!!)
    }

    override fun onBindViewHolder(holder: ViewHolder?, item: TimeZoneData?, position: Int) {
        holder as ViewHolder
        item as TimeZoneData
        if (position == 0 || list[position - 1].getFirst() != item.getFirst()) {
            holder.capital.visibility = View.VISIBLE
        } else {
            holder.capital.visibility = View.GONE
        }
        holder.capital.text = item.getFirst()
        holder.title.text = item.chineseName
    }

    fun getList(): ArrayList<TimeZoneData> = list as ArrayList
}