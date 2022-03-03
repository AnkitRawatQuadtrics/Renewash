package com.quadtric.renewash.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.quadtric.renewash.R
import com.quadtric.renewash.commonFunctions.Common
import com.quadtric.renewash.databinding.FragmentChangePasswordBinding
import com.quadtric.renewash.databinding.FragmentLoginBinding
import com.quadtric.renewash.databinding.FragmentPaymentOptionBinding
import com.quadtric.renewash.models.fillinformationModels.CommonPojo
import com.quadtric.renewash.viewModels.ForgotPasswordViewModel
import com.quadtric.renewash.viewModels.SignUpViewModels

class ChangePasswordFragment : Fragment() {
    private lateinit var binding: FragmentChangePasswordBinding
    private var map: HashMap<String, String> = HashMap()
    private val model: ForgotPasswordViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangePasswordBinding.inflate(layoutInflater)
        click()
        return binding.root
    }

    private fun click() {
        binding.apply {
            backButton.setOnClickListener {
                requireActivity().onBackPressed()
            }

            resetPassword.setOnClickListener{
                when {
/*
                    passwordEditText.text.isEmpty() -> {
                        passwordEditText.error = "Password Field is Required"
                        Toast.makeText(requireContext(), "Password Field is Required", Toast.LENGTH_SHORT)
                            .show()
                    }
*/
/*
                    confirmPasswordEditText.text.isEmpty() -> {
                        confirmPasswordEditText.error = "Confirm Password Field is Required"
                        Toast.makeText(
                            requireContext(),
                            "Confirm Password Field is Required",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
*/
                    emailEditText.text.isEmpty() -> {
                        emailEditText.error = "Email Field is Required"
                        Toast.makeText(requireContext(), "Email Field is Required", Toast.LENGTH_SHORT)
                            .show()
                    }

                    else -> {
                        if (Common.checkForInternet(requireContext())) {
                            map["u_username"] = emailEditText.text.toString()
                            model.forgotPassword(
                                map,requireView(),requireContext()
                            ).observe(viewLifecycleOwner, Observer<CommonPojo?> { model ->
                                // update UI
                                Log.e("FORGOT_MESSAGE", model.message.toString())
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
            }
        }
    }




}