package com.parallel.hati.randomalarm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment() {
    private lateinit var mRealm : Realm

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Realm.init(this.getContext())
        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        mRealm = Realm.getInstance(realmConfig)

        var alarms = mRealm.where(Alarm::class.java).findAll()

        if (alarms.size < 1) {
            mRealm.executeTransaction {
                val alarm = mRealm.createObject(Alarm::class.java, 0)
            }
            // alarms = mRealm.where(Alarm::class.java).findAll()
        }

        val listView = view.findViewById(R.id.time_list_view) as ListView
        val adapter = MainAdapter(this.getContext()!!, alarms)
        listView.adapter = adapter
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val alarm = adapter.getItem(position)
                when(id) {
                    R.id.onoff_button.toLong() ->
                        if (alarm != null) {
                            val alarm_id = alarm.id
                            val hour = alarm.hour
                            val minute = alarm.minute
                            val content = MainFragmentDirections.actionMainFragmentToTimeFragment(
                                alarm_id,
                                hour,
                                minute
                            )
                            findNavController().navigate(content)
                        }
                    R.id.time_button.toLong() ->
                        if (alarm != null) {
                            val alarm_id = alarm.id
                            val content = MainFragmentDirections.actionMainFragmentToSettingsFragment(
                                alarm_id,
                                alarm.hour,
                                alarm.minute
                            )
                            findNavController().navigate(content)
                        }
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }
}
