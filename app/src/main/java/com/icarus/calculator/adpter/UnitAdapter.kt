package com.icarus.calculator.adpter

import a.icarus.impl.RecyclerAdapter
import a.icarus.utils.Logger
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.icarus.calculator.R
import com.icarus.calculator.data.UnitInfo

class UnitAdapter(layoutId: Int, list: List<UnitInfo>) :
    RecyclerAdapter<UnitInfo, UnitAdapter.ViewHolder>(layoutId, list) {
    var listener: ((position: Int) -> Unit)? = null
    var baseValue = "100"
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var ignore: Int = -1
    override fun onCreateViewHolder(view: View?): ViewHolder {
        return ViewHolder(view as View)
    }

    override fun onBindViewHolder(holder: ViewHolder?, item: UnitInfo?, position: Int) {
        item as UnitInfo
        holder as ViewHolder
        if (position == ignore) {
            holder.root.visibility = View.GONE
        } else {
            holder.root.visibility = View.VISIBLE
            holder.name.text = item.name
            holder.symbol.text = item.symbol
            holder.value.text = item.fromBaseUnit(baseValue)
            val icon = item.icon ?: 0
            if (icon == 0) {
                holder.icon.visibility = View.GONE
            } else {
                holder.icon.visibility = View.VISIBLE
                holder.icon.setImageResource(icon)
            }
            holder.itemView.setOnClickListener {
                listener?.run { this(position) }
            }
        }
    }

    class ViewHolder(view: View) : RecyclerAdapter.ViewHolder(view) {
        var name: TextView = findViewById(R.id.name)
        var symbol: TextView = findViewById(R.id.symbol)
        var value: TextView = findViewById(R.id.value)
        var icon: ImageView = findViewById(R.id.icon)
        var root: View = findViewById(R.id.hide_root)
    }
}