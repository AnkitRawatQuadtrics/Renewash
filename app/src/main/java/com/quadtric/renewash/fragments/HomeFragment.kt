package com.quadtric.renewash.fragments

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.quadtric.renewash.R
import com.quadtric.renewash.commonFunctions.SharedPreference.Companion.PREF_NAME
import com.quadtric.renewash.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        clicks()
        clearSharedPref()
        return binding.root
    }

    private fun clearSharedPref() {
        val settings = requireContext()
            .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        settings.edit().clear().apply()
    }


    private fun clicks() {
        binding.bookAppointmentLinear.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_homeFragment_to_bookAppointmentFragment)
        }
        binding.purchaseGiftCardLinear.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_homeFragment_to_giftCardFragment)
        }
        binding.contactUsLinearLayout.setOnClickListener {
            Toast.makeText(requireContext(), "Coming Soon!!", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onResume() {
        super.onResume()
        requireView().isFocusableInTouchMode = true
        requireView().requestFocus()
        requireView().setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                //your code
                requireActivity().finish()
                true
            } else false
        }
    }
}