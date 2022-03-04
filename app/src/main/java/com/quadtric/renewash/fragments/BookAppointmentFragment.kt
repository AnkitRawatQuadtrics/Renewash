package com.quadtric.renewash.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.quadtric.renewash.R
import com.quadtric.renewash.commonFunctions.SharedPreference
import com.quadtric.renewash.databinding.FragmentBookAppointmentBinding

class BookAppointmentFragment : Fragment() {
    private lateinit var binding: FragmentBookAppointmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookAppointmentBinding.inflate(layoutInflater)
        clicks()
        return binding.root
    }


    private fun clicks() {
        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.oneTimeWashLinear.setOnClickListener {
           /* Navigation.findNavController(requireView())
                .navigate(R.id.action_bookAppointmentFragment_to_vehicleInformationFragment)*/
            Navigation.findNavController(requireView())
                .navigate(R.id.action_bookAppointmentFragment_to_addVehicleDetailFragment)
            SharedPreference.setStringPref(requireContext(),SharedPreference.qt_type,"0")
        }
        binding.subscriptionLinear.setOnClickListener {
          /*  Navigation.findNavController(requireView())
                .navigate(R.id.action_bookAppointmentFragment_to_vehicleInformationFragment)*/
            Navigation.findNavController(requireView())
                .navigate(R.id.action_bookAppointmentFragment_to_addVehicleDetailFragment)
            SharedPreference.setStringPref(requireContext(),SharedPreference.qt_type,"1")
        }
    }

}