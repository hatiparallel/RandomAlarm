package com.parallel.hati.randomalarm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import io.realm.RealmList
import io.realm.RealmResults
import java.util.*

class MainAdapter(context : Context, alarms : RealmResults<Alarm>) : BaseAdapter() {
    private val mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val alarms = alarms

    override fun getItem(position : Int) : Alarm? {
        return alarms[position]
    }

    override fun getItemId(position : Int) : Long {
        return position.toLong()
    }

    override fun getCount() : Int {
        return alarms.size
    }

    override public fun getView(position : Int, view : View?, parent : ViewGroup): View {
        var view = view
        if (view == null) {
            view = mInflater.inflate(R.layout.view_main, null)
        }

        val item = this.getItem(position)
        if (item != null) {
            var hour_string = item.hour.toString()
            var minute_string = item.minute.toString()
            if (hour_string.length == 1) {
                hour_string = "0" + hour_string
            }
            if (minute_string.length == 1) {
                minute_string = "0" + minute_string
            }
            val shown : String = hour_string + " : " + minute_string
            view!!.findViewById<TextView>(R.id.time_button).setText(shown)

            view!!.findViewById<Button>(R.id.onoff_button).setOnClickListener {
                (parent as ListView).performItemClick(view, position, R.id.onoff_button.toLong())
            }

            view!!.findViewById<Button>(R.id.time_button).setOnClickListener {
                (parent as ListView).performItemClick(view, position, R.id.time_button.toLong())
            }
        }
        return view!!
    }
}