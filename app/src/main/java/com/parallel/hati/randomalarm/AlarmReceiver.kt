package com.parallel.hati.randomalarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver() : BroadcastReceiver() {
    override fun onReceive(context : Context, intent : Intent) {
        Log.d("MMM", "received")
        return
    }
}