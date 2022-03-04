package com.quadtric.renewash.fragments

import android.os.Bundle
import android.text.InputFilter
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.quadtric.renewash.R
import com.quadtric.renewash.commonFunctions.Common
import com.quadtric.renewash.databinding.FragmentSignUpBinding
import com.quadtric.renewash.models.fillinformationModels.CommonPojo
import com.quadtric.renewash.viewModels.SignUpViewModels

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private var map: HashMap<String, String> = HashMap()
    private val model: SignUpViewModels by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        val builder = SpannableStringBuilder()
        val grey = "Have an account?"
        val greySpan = SpannableString(grey)
        greySpan.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.dark_grey
                )
            ), 0, grey.length, 0
        )
        builder.append(greySpan)
        val blue = " Sign In"
        val whiteSpannable = SpannableString(blue)
        whiteSpannable.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.blue
                )
            ), 0, blue.length, 0
        )
        builder.append(whiteSpannable)
        binding.signInTexView.setText(builder, TextView.BufferType.SPANNABLE)
        clicks()
        ignoreSpecialChars()
        return binding.root
    }

    private fun addUserInformation() {
        map["u_firstname"] = binding.firstName.text.toString()
        map["u_lastname"] = binding.lastName.text.toString()
        map["u_email"] = binding.emailEditText.text.toString()
        map["u_password"] = binding.passwordEditText.text.toString()
        map["u_confirmpassowrd"] = binding.confirmPasswordEditText.text.toString()
        map["u_phonenumber"] = binding.phoneEditText.text.toString()
        map["u_address"] = binding.addressEditText.text.toString()
        map["device_type"] = "Android"
        map["device_token"] = "1"
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
        binding.phoneEditText.filters = arrayOf(filter, InputFilter.LengthFilter(15))
    }

    private fun sendUserInformation() {
        if (binding.firstName.text.isEmpty()) {
            binding.firstName.error = "First Name Field is Required"
            Toast.makeText(requireContext(), "First Name Field is Required", Toast.LENGTH_SHORT)
                .show()
        } else if (binding.lastName.text.isEmpty()) {
            binding.lastName.error = "Last Name Field is Required"
            Toast.makeText(requireContext(), "Last Name Field is Required", Toast.LENGTH_SHORT)
                .show()
        } else if (binding.emailEditText.text.isEmpty()) {
            binding.emailEditText.error = "Email Field is Required"
            Toast.makeText(requireContext(), "Email Field is Required", Toast.LENGTH_SHORT)
                .show()
        } else if (!Common.isValidEmail(binding.emailEditText.text.toString())) {
            binding.emailEditText.error = "Please Enter Valid Email."
            Toast.makeText(activity, "Please Enter Valid Email.", Toast.LENGTH_SHORT).show()
        } else if (binding.phoneEditText.text.toString().isEmpty()) {
            binding.phoneEditText.error = "Phone Number field is required."
            Toast.makeText(activity, "Phone Number field is required.", Toast.LENGTH_SHORT)
                .show()
        } else if (binding.phoneEditText.text.toString().length < 9) {
            binding.phoneEditText.error = "Please Enter Valid Phone Number"
            Toast.makeText(activity, "Please Enter Valid Phone Number.", Toast.LENGTH_SHORT)
                .show()
        } else if (binding.passwordEditText.text.isEmpty()) {
            binding.passwordEditText.error = "Password Field is Required"
            Toast.makeText(requireContext(), "Password Field is Required", Toast.LENGTH_SHORT)
                .show()
        } else if (binding.confirmPasswordEditText.text.isEmpty()) {
            binding.confirmPasswordEditText.error = "Confirm Password Field is Required"
            Toast.makeText(
                requireContext(),
                "Confirm Password Field is Required",
                Toast.LENGTH_SHORT
            )
                .show()
        } else if (binding.passwordEditText.text.toString() != binding.confirmPasswordEditText.text.toString()) {
            binding.confirmPasswordEditText.error = "Password Not Matched"
            Toast.makeText(requireContext(), "Password Not Matched", Toast.LENGTH_SHORT)
                .show()
        } else if (binding.addressEditText.text.isEmpty()) {
            binding.addressEditText.error = "Address Field is Required"
            Toast.makeText(requireContext(), "Address Field is Required", Toast.LENGTH_SHORT)
                .show()
        } else {
            if (Common.checkForInternet(requireContext())) {
                addUserInformation()
                model.signInApi(
                    map, requireView(), requireContext()
                ).observe(viewLifecycleOwner, Observer<CommonPojo?> { model ->
                    // update UI
                    Log.e("SIGN_UP_MESSAGE", model.message.toString())
                })
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please Check Your Internet Connection.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun clicks() {
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.signInTexView.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.signUpBtn.setOnClickListener {
            sendUserInformation()
        }
    }

}