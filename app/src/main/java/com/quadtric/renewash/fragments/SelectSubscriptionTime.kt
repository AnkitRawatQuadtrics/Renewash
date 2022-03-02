package com.quadtric.renewash.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.quadtric.renewash.databinding.FragmentSubscriptionTimeBinding
import com.quadtric.renewash.models.dayTimeModels.DayTimeData
import com.quadtric.renewash.models.dayTimeModels.DayTimePojo
import com.quadtric.renewash.models.dayTimeModels.TimeData
import com.quadtric.renewash.viewModels.DayTimeViewModel


class SelectSubscriptionTime : Fragment() {
    private lateinit var binding: FragmentSubscriptionTimeBinding
    private val model: DayTimeViewModel by viewModels()
    private lateinit var timeList: List<TimeData>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubscriptionTimeBinding.inflate(layoutInflater)
        clicks()
        return binding.root
    }

    private fun clicks() {
        binding.dayTextView.setOnClickListener {
            model.getDayDate(requireContext())
                .observe(viewLifecycleOwner, Observer<DayTimePojo> { model ->
                    // update UI
                    showDays(model.data)
                })
        }
        binding.timeTextView.setOnClickListener {
            if (timeList.isNotEmpty()) {
                showTime(timeList)
            } else {
                Toast.makeText(requireContext(), "Select Day First", Toast.LENGTH_SHORT).show()
            }
        }
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun showDays(data: List<DayTimeData>?) {
        val popupMenu = PopupMenu(requireContext(), binding.dayTextView)
        for (i in 0 until data!!.size) {
            popupMenu.menu.add(data[i].tDay)
        }
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            binding.dayTextView.text = item.title.toString()
            val paymentType = item.title.toString()
            val paymentId = item.itemId
            Log.e("dcfdfscsaas", paymentId.toString())
            timeList = data[0].timeduration
            true
        })
        popupMenu.show()

    }

    private fun showTime(data: List<TimeData>?) {
        val popupMenu = PopupMenu(requireContext(), binding.timeTextView)
        for (i in 0 until data!!.size) {
            popupMenu.menu.add(data[i].time)
        }
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            binding.timeTextView.text = item.title.toString()
            val paymentType = item.title.toString()
            true
        })
        popupMenu.show()

    }


}