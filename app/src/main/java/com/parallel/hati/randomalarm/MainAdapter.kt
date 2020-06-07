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
            val hour = item.hour
            val minute = item.minute
            val shown : String = hour.toString() + " : " + minute.toString()
            view!!.findViewById<TextView>(R.id.time_view).setText(shown)

            view!!.findViewById<Button>(R.id.onoff_button).setOnClickListener {
                (parent as ListView).performItemClick(view, position, R.id.onoff_button.toLong())
            }
        }
        return view!!
    }
}