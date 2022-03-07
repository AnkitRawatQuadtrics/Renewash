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
import com.quadtric.renewash.models.dayDateModel.DayDateData
import com.quadtric.renewash.models.dayDateModel.DayDatePojo
import com.quadtric.renewash.models.dayDateModel.TimeData
import com.quadtric.renewash.viewModels.DayDateViewModel
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


class CarWashDateTimeFragment : Fragment()/*, DayDateAdapter.DateClick */{
    private lateinit var binding: FragmentCarWashDateTimeBinding
    private val model: DayDateViewModel by viewModels()
    private lateinit var ctx: Context
    private lateinit var dayDateAdapter: DayDateAdapter
    private lateinit var timeAdapter: TimeAdapter
    private lateinit var currentDate: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarWashDateTimeBinding.inflate(layoutInflater)
        if (Common.checkForInternet(requireContext())) {
            model.getDayDate(ctx).observe(viewLifecycleOwner, Observer<DayDatePojo> { model ->

                showCalender(model.data)
            })
        } else {
            Toast.makeText(ctx, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show()
        }
        click()
        initTimeAdapter()
        return binding.root
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }
     @SuppressLint("NewApi", "SimpleDateFormat")
     private fun showCalender(data: List<DayDateData>){
         // Add Listener in calendar
         // Add Listener in calendar
         val calendar = Calendar.getInstance(TimeZone.getDefault())
         val currentYear = calendar[Calendar.YEAR]
         val currentMonth = calendar[Calendar.MONTH] + 1
         val currentDay = calendar[Calendar.DAY_OF_MONTH]
         binding.calendar.minDate= System.currentTimeMillis() - 1000
         val sdf = SimpleDateFormat("YYYY-MM-dd")
         /*val maxDate: Date = sdf.parse("2022-04-07")
         binding.calendar.maxDate = maxDate.time*/
        /* val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
         binding.calendar.maxDate = daysInMonth.toLong()*/
         val formatter: NumberFormat = DecimalFormat("00")
         val monthF: String = formatter.format(currentMonth) // ----> 01
         val dayF: String = formatter.format(currentDay) // ----> 01
         currentDate = "$monthF-$dayF-$currentYear"
         SharedPreference.setStringPref(requireContext(),SharedPreference.date,currentDate)
         for (i in data.indices){
             if (currentDate == data[i].date){
                 callTimeAdapter(data[i].timeArr)
             }
         }

         binding.calendar
             .setOnDateChangeListener(
                 OnDateChangeListener { view, year, month, dayOfMonth ->
                        calendar.set(year,month,dayOfMonth)
                     val monthF: String = formatter.format(month + 1) // ----> 01
                     val dayF: String = formatter.format(dayOfMonth) // ----> 01
                     currentDate = ("$monthF-$dayF-$year")
                     SharedPreference.setStringPref(requireContext(),SharedPreference.date,currentDate)
                     Toast.makeText(requireContext(),currentDate,Toast.LENGTH_SHORT).show()
                     for (i in data.indices){
                         if (currentDate == data[i].date){
                             callTimeAdapter(data[i].timeArr)
                         }
                     }
                 })

     }

 /*   @SuppressLint("NotifyDataSetChanged")
    private fun callDayDateAdapter(dayDateData: List<DayDateData>?) {
        dayDateAdapter = DayDateAdapter(ctx, dayDateData!!, this)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.dayDateRecyclerView.layoutManager = layoutManager
        binding.dayDateRecyclerView.itemAnimator = DefaultItemAnimator()
        binding.dayDateRecyclerView.adapter = dayDateAdapter
    }*/

    private fun initTimeAdapter() {
        timeAdapter = TimeAdapter(ctx)
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        binding.timeRecyclerView.layoutManager =
            gridLayoutManager // set LayoutManager to RecyclerView
        binding.timeRecyclerView.itemAnimator = DefaultItemAnimator()
        binding.timeRecyclerView.adapter = timeAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun callTimeAdapter(list: ArrayList<TimeData>) {
        binding.timeRecyclerView.post {
            timeAdapter.timeData = list
            timeAdapter.notifyDataSetChanged()
        }
    }

    private fun click() {
        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.nextButton.setOnClickListener {
            if (SharedPreference.getStringPref(ctx, "date_time")!!.isEmpty()) {
                Toast.makeText(ctx, "Please Select Date and Time.", Toast.LENGTH_SHORT).show()
            } else {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_carWashDateTimeFragment_to_fillInformationFragment)
            }
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