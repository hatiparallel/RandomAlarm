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
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment() {
    private val args: MainFragmentArgs by navArgs()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("MMM", "3")

        val listView = view.findViewById(R.id.time_list_view) as ListView
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, args.hour)
        calendar.set(Calendar.MINUTE, args.minute)
        val list : List<Calendar> = listOf(calendar)
        val adapter = MainAdapter(this.getContext()!!, list)
        listView.adapter = adapter
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val calendar = adapter.getItem(position)
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                val minute = calendar.get(Calendar.MINUTE)
                val content = MainFragmentDirections.actionMainFragmentToTimeFragment(position, hour, minute)
                findNavController().navigate(content)
            }

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_TimeFragment)
        }
    }
}
