package com.parallel.hati.randomalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
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

class SettingsFragment : Fragment() {
    private val args: SettingsFragmentArgs by navArgs()
    private lateinit var mRealm : Realm

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }



    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        mRealm = Realm.getInstance(realmConfig)

        val alarm = mRealm.where(Alarm::class.java).equalTo("id", args.id).findFirst()

        view.findViewById<Button>(R.id.back_button).setOnClickListener {
            val content = SettingsFragmentDirections.actionSettingsFragmentToMainFragment(0, 0)
            findNavController().navigate(content)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }
}