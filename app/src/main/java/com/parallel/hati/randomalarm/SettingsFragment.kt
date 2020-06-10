package com.parallel.hati.randomalarm

import android.app.Activity
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

    private val audioRequestCode = 283

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

        view.findViewById<Button>(R.id.button_add_audio).setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.setType("audio/*")
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            // todo : resolve activity
            // todo : open multiple files
            startActivityForResult(intent, audioRequestCode)
        }

        view.findViewById<Button>(R.id.button_settings_to_main).setOnClickListener {
            val content = SettingsFragmentDirections.actionSettingsFragmentToMainFragment()
            findNavController().navigate(content)
        }

        view.findViewById<Button>(R.id.button_settings_to_time).setOnClickListener {
            val content = SettingsFragmentDirections.actionSettingsFragmentToTimeFragment(args.id, args.hour, args.minute)
            findNavController().navigate(content)
        }
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        if (requestCode == audioRequestCode && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                if (data.getData() != null) {
                    val uri = data.getData()
                    Log.d("AUD", uri!!.toString())
                } else if (data.getClipData() != null) {
                    val cd = data.getClipData()
                    for (i in 0..cd!!.getItemCount()-1) {
                        val uri = cd.getItemAt(i).getUri()
                        Log.d("AUD", uri!!.toString())
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }
}