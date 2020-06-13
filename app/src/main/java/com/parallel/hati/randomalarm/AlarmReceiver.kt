package com.parallel.hati.randomalarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AlarmReceiver() : BroadcastReceiver() {
    override fun onReceive(context : Context, intent : Intent) {
        Log.d("MMM", "received")
        Toast.makeText(context,"Received", Toast.LENGTH_LONG).show()
        val wakeupIntent = Intent(context, WakeupActivity::class.java)
        wakeupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(wakeupIntent)
        return
    }
}