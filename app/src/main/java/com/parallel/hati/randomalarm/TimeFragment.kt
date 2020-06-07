package com.parallel.hati.randomalarm

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
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

        val alarm = mRealm.where(Alarm::class.java).equalTo("id", id).findFirst()

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

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
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
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)

            val content = TimeFragmentDirections.actionTimeFragmentToMainFragment(hour, minute)
            findNavController().navigate(content)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }
}
