package com.parallel.hati.randomalarm

import android.os.Bundle
import android.widget.Button
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity

class WakeupActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wakeup)

        findViewById<Button>(R.id.snooze_button).setOnClickListener {
            return@setOnClickListener
        }

        findViewById<ToggleButton>(R.id.off_button).setOnClickListener {
            return@setOnClickListener
        }
    }
}