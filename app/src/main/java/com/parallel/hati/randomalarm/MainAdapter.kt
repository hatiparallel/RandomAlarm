package com.parallel.hati.randomalarm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import java.util.*

class MainAdapter(context : Context, calendars : List<Calendar>) : BaseAdapter() {
    private val mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val calendars = calendars

    override fun getItem(position : Int) : Calendar {
        return calendars[position]
    }

    override fun getItemId(position : Int) : Long {
        return position.toLong()
    }

    override fun getCount() : Int {
        return calendars.size
    }

    override public fun getView(position : Int, view : View?, parent : ViewGroup): View {
        var view = view
        if (view == null) {
            view = mInflater.inflate(R.layout.view_main, null)
        }

        val item = this.getItem(position)
        if (item != null) {
            val hour = item.get(Calendar.HOUR_OF_DAY)
            val minute = item.get(Calendar.MINUTE)
            val shown : String = hour.toString() + " : " + minute.toString()
            view!!.findViewById<TextView>(R.id.time_view).setText(shown)

            view!!.findViewById<Button>(R.id.onoff_button).setOnClickListener {
                (parent as ListView).performItemClick(view, position, R.id.onoff_button.toLong())
            }
        }
        return view!!
    }
}