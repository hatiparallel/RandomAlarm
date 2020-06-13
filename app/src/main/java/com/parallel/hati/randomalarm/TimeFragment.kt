package com.parallel.hati.randomalarm

import android.app.AlarmManager
import android.app.AlarmManager.AlarmClockInfo
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import io.realm.Realm
import io.realm.RealmConfiguration
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class TimeFragment : Fragment() {
    private val args: TimeFragmentArgs by navArgs()
    private lateinit var mRealm : Realm

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time, container, false)
    }



    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        mRealm = Realm.getInstance(realmConfig)

        val alarm = mRealm.where(Alarm::class.java).equalTo("id", args.id).findFirst()

        val timePicker = view.findViewById<TimePicker>(R.id.time_picker)

        var hour = args.hour
        var minute = args.minute

        val currentApiVersion = Build.VERSION.SDK_INT
        if (currentApiVersion >= Build.VERSION_CODES.M) {
            timePicker.setHour(hour);
            timePicker.setMinute(minute);
        } else {
            timePicker.setCurrentHour(hour);
            timePicker.setCurrentMinute(minute);
        }

        view.findViewById<Button>(R.id.button_times_to_main).setOnClickListener {
            if (currentApiVersion >= Build.VERSION_CODES.M) {
                hour = timePicker.getHour();
                minute = timePicker.getMinute();

            } else {
                hour = timePicker.getCurrentHour();
                minute = timePicker.getCurrentMinute();
            }

            mRealm.executeTransaction {
                alarm?.hour = hour
                alarm?.minute = minute
            }

            val calendar : Calendar = Calendar.getInstance()
            calendar.setTimeInMillis(System.currentTimeMillis())
            Log.d("MMM", calendar.get(Calendar.DATE).toString())
            Log.d("MMM", calendar.get(Calendar.MONTH).toString())
            Log.d("MMM", calendar.get(Calendar.YEAR).toString())
            Log.d("MMM", calendar.get(Calendar.HOUR_OF_DAY).toString())
            Log.d("MMM", calendar.get(Calendar.MINUTE).toString())
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
                calendar.add(Calendar.DATE, 1)
            }
            Log.d("MMM", calendar.get(Calendar.DATE).toString())
            Log.d("MMM", calendar.get(Calendar.MONTH).toString())
            Log.d("MMM", calendar.get(Calendar.YEAR).toString())
            Log.d("MMM", calendar.get(Calendar.HOUR_OF_DAY).toString())
            Log.d("MMM", calendar.get(Calendar.MINUTE).toString())

            val intent = Intent(this.getContext(), AlarmReceiver::class.java)
            val pending = PendingIntent.getBroadcast(this.getContext(), 0, intent, 0)
            val manager = this.getContext()!!.getSystemService(ALARM_SERVICE) as AlarmManager
            Log.d("MMM", manager.toString())
            // manager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                manager.setAlarmClock(
                    AlarmClockInfo(calendar.timeInMillis, null),
                    pending
                )
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                manager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pending)
            } else {
                manager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pending)
            }

            val content = TimeFragmentDirections.actionTimeFragmentToMainFragment()
            findNavController().navigate(content)
        }

        view.findViewById<Button>(R.id.button_times_to_settings).setOnClickListener {
            if (alarm?.musiclist != null && alarm.musiclist.size < 1) {
                Toast.makeText(this.getContext(), "no music", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (currentApiVersion >= Build.VERSION_CODES.M) {
                hour = timePicker.getHour();
                minute = timePicker.getMinute();

            } else {
                hour = timePicker.getCurrentHour();
                minute = timePicker.getCurrentMinute();
            }

            mRealm.executeTransaction {
                alarm?.hour = hour
                alarm?.minute = minute
            }

            val calendar : Calendar = Calendar.getInstance()
            calendar.setTimeInMillis(System.currentTimeMillis())
            Log.d("MMM", calendar.get(Calendar.DATE).toString())
            Log.d("MMM", calendar.get(Calendar.MONTH).toString())
            Log.d("MMM", calendar.get(Calendar.YEAR).toString())
            Log.d("MMM", calendar.get(Calendar.HOUR_OF_DAY).toString())
            Log.d("MMM", calendar.get(Calendar.MINUTE).toString())
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
                calendar.add(Calendar.DATE, 1)
            }
            Log.d("MMM", calendar.get(Calendar.DATE).toString())
            Log.d("MMM", calendar.get(Calendar.MONTH).toString())
            Log.d("MMM", calendar.get(Calendar.YEAR).toString())
            Log.d("MMM", calendar.get(Calendar.HOUR_OF_DAY).toString())
            Log.d("MMM", calendar.get(Calendar.MINUTE).toString())

            val intent = Intent(this.getContext(), AlarmReceiver::class.java)
            val pending = PendingIntent.getBroadcast(this.getContext(), 0, intent, 0)
            val manager = this.getContext()!!.getSystemService(ALARM_SERVICE) as AlarmManager
            Log.d("MMM", manager.toString())
            // manager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                manager.setAlarmClock(
                    AlarmClockInfo(calendar.timeInMillis, null),
                    pending
                )
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                manager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pending)
            } else {
                manager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pending)
            }

            val content = TimeFragmentDirections.actionTimeFragmentToSettingsFragment(args.id, args.hour, args.minute)
            findNavController().navigate(content)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }
}
