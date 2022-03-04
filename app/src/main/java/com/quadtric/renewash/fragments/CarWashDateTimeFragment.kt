package com.quadtric.renewash.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView.OnDateChangeListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.quadtric.renewash.R
import com.quadtric.renewash.adapters.DayDateAdapter
import com.quadtric.renewash.adapters.TimeAdapter
import com.quadtric.renewash.commonFunctions.Common
import com.quadtric.renewash.commonFunctions.SharedPreference
import com.quadtric.renewash.databinding.FragmentCarWashDateTimeBinding
import com.quadtric.renewash.models.dayDateModel.DayDatePojo
import com.quadtric.renewash.models.dayDateModel.TimeData
import com.quadtric.renewash.viewModels.DayDateViewModel
import java.util.*
import kotlin.collections.ArrayList


class CarWashDateTimeFragment : Fragment()/*, DayDateAdapter.DateClick */{
    private lateinit var binding: FragmentCarWashDateTimeBinding
    private val model: DayDateViewModel by viewModels()
    private lateinit var ctx: Context
    private lateinit var dayDateAdapter: DayDateAdapter
    private lateinit var timeAdapter: TimeAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarWashDateTimeBinding.inflate(layoutInflater)
        showCalender()
      /*  if (Common.checkForInternet(requireContext())) {
            model.getDayDate(ctx).observe(viewLifecycleOwner, Observer<DayDatePojo> { model ->
//                callDayDateAdapter(model.data)
            })
        } else {
            Toast.makeText(ctx, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show()
        }*/
        click()
//        initTimeAdapter()
        return binding.root
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }
     private fun showCalender(){
         // Add Listener in calendar
         // Add Listener in calendar
         val calendar1: Calendar = Calendar.getInstance()
         binding.calendar.minDate = 1
         binding.calendar.maxDate = 30
         binding.calendar
             .setOnDateChangeListener(
                 OnDateChangeListener { view, year, month, dayOfMonth ->
                     // In this Listener have one method
                     // and in this method we will
                     // get the value of DAYS, MONTH, YEARS
                     // Store the value of date with
                     // format in String type Variable
                     // Add 1 in month because month
                     // index is start with 0
                     val date = (dayOfMonth.toString() + "-"
                             + (month + 1) + "-" + year)
                     // set this date in TextView for Display
                     Toast.makeText(requireContext(),date,Toast.LENGTH_SHORT).show()
                 })
     }
    @SuppressLint("NotifyDataSetChanged")
//    private fun callDayDateAdapter(dayDateData: List<DayDateData>?) {
//        dayDateAdapter = DayDateAdapter(ctx, dayDateData!!, this)
//        val layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        binding.dayDateRecyclerView.layoutManager = layoutManager
//        binding.dayDateRecyclerView.itemAnimator = DefaultItemAnimator()
//        binding.dayDateRecyclerView.adapter = dayDateAdapter
//    }

   /* private fun initTimeAdapter() {
        timeAdapter = TimeAdapter(ctx)
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        binding.timeRecyclerView.layoutManager =
            gridLayoutManager // set LayoutManager to RecyclerView
        binding.timeRecyclerView.itemAnimator = DefaultItemAnimator()
        binding.timeRecyclerView.adapter = timeAdapter
    }*/

   /* @SuppressLint("NotifyDataSetChanged")
    private fun callTimeAdapter(list: ArrayList<TimeData>) {
        binding.timeRecyclerView.post {
            timeAdapter.timeData = list
            timeAdapter.notifyDataSetChanged()
        }
    }*/

    private fun click() {
        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.nextButton.setOnClickListener {
          /*  if (SharedPreference.getStringPref(ctx, "date_time")!!.isEmpty()) {
                Toast.makeText(ctx, "Please Select Date and Time.", Toast.LENGTH_SHORT).show()
            } else {*/
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_carWashDateTimeFragment_to_fillInformationFragment)
//            }
        }
        binding.summaryBottomSheet.setOnClickListener {
            Common.showSummaryDialog(
                requireActivity(),
                SharedPreference.getStringPref(requireActivity(), SharedPreference.vehicle_type)
                    .toString(),
                SharedPreference.getStringPref(requireActivity(), SharedPreference.vehicle_package)
                    .toString(),
                SharedPreference.getStringPref(ctx, SharedPreference.service_name).toString(),
                SharedPreference.getStringPref(requireActivity(), SharedPreference.date).toString(),
                SharedPreference.getStringPref(requireActivity(), SharedPreference.time).toString(),
                SharedPreference.getStringPref(requireActivity(), SharedPreference.total).toString()
            )
        }
    }

    /*override fun dateClick(dayDateData: ArrayList<TimeData>) {
        callTimeAdapter(dayDateData)
    }*/
}