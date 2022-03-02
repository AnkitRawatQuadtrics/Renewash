package com.quadtric.renewash.fragments

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.quadtric.renewash.R
import com.quadtric.renewash.commonFunctions.Common
import com.quadtric.renewash.commonFunctions.SharedPreference
import com.quadtric.renewash.databinding.FragmentFillInformationBinding


class FillInformationFragment : Fragment() {
    private lateinit var binding: FragmentFillInformationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFillInformationBinding.inflate(layoutInflater)
        click()
        ignoreSpecialChars()
        return binding.root
    }

    private fun ignoreSpecialChars() {
        val filter =
            InputFilter { source, start, end, dest, dstart, dend ->
                for (i in start until end) {
                    if (!Character.isLetterOrDigit(source[i])) {
                        return@InputFilter ""
                    }
                }
                null
            }
        binding.phone.filters = arrayOf(filter)
    }


    private fun click() {
        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.previousButton.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.nextButton.setOnClickListener {
            when {
                binding.firstName.text.toString().isEmpty() -> {
                    binding.firstName.error = "First Name field is required."
                    Toast.makeText(activity, "First Name field is required.", Toast.LENGTH_SHORT)
                        .show()
                }
                binding.lastName.text.toString().isEmpty() -> {
                    binding.lastName.error = "Last Name is required."
                    Toast.makeText(activity, "Last Name is required.", Toast.LENGTH_SHORT).show()
                }
                binding.email.text.toString().isEmpty() -> {
                    binding.email.error = "Email field is required."
                    Toast.makeText(activity, "Email field is required.", Toast.LENGTH_SHORT).show()
                }
                 !Common.isValidEmail(binding.email.text.toString())->{
                     binding.email.error = "Please Enter Valid Email."
                     Toast.makeText(activity, "Please Enter Valid Email.", Toast.LENGTH_SHORT)
                         .show()
                }
                binding.phone.text.toString().isEmpty() -> {
                    binding.phone.error = "Phone Number field is required."
                    Toast.makeText(activity, "Phone Number field is required.", Toast.LENGTH_SHORT)
                        .show()
                }
                binding.address.text.toString().isEmpty() -> {
                    binding.address.error = "Address field is required."
                    Toast.makeText(activity, "Address field is required.", Toast.LENGTH_SHORT)
                        .show()
                }
                binding.message.text.toString().isEmpty() -> {
                    binding.message.error = "Message field is required."
                    Toast.makeText(activity, "Message field is required.", Toast.LENGTH_SHORT)
                        .show()
                }
                !binding.termsAndCondition.isChecked -> {
                    Toast.makeText(requireContext(), "Agree Terms & Conditions", Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    val bundle = Bundle()
                    bundle.putString("first_name", binding.firstName.text.toString())
                    bundle.putString("last_name", binding.lastName.text.toString())
                    bundle.putString("email", binding.email.text.toString())
                    bundle.putString("phone_number", binding.phone.text.toString())
                    bundle.putString("address", binding.address.text.toString())
                    bundle.putString("message", binding.message.text.toString())
                    Navigation.findNavController(requireView())
                        .navigate(
                            R.id.action_fillInformationFragment_to_paymentOptionFragment, bundle
                        )
                }

            }
        }
        binding.summaryBottomSheet.setOnClickListener {
            Common.showSummaryDialog(
                requireActivity(), SharedPreference.getStringPref(
                    requireActivity(),
                    SharedPreference.vehicle_type
                ).toString(),
                SharedPreference.getStringPref(
                    requireActivity(), SharedPreference.vehicle_package
                ).toString(),
                SharedPreference.getStringPref(requireActivity(),SharedPreference.service_name).toString(),
                SharedPreference.getStringPref(requireActivity(), SharedPreference.date)
                    .toString(),
                SharedPreference.getStringPref(requireActivity(), SharedPreference.time)
                    .toString(),
                SharedPreference.getStringPref(requireActivity(), SharedPreference.total)
                    .toString()
            )
        }
    }
}