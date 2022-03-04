package com.quadtric.renewash.fragments

import android.os.Bundle
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
import com.quadtric.renewash.databinding.FragmentLoginBinding
import com.quadtric.renewash.models.CommonResponse
import com.quadtric.renewash.models.fillinformationModels.CommonPojo
import com.quadtric.renewash.viewModels.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private var map: HashMap<String, String> = HashMap()
    private val model: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentLoginBinding.inflate(layoutInflater)
        val builder = SpannableStringBuilder()
        val grey = "Don't have an account?"
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
        val blue = " Sign Up"
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
        binding.signInTextView.setText(builder, TextView.BufferType.SPANNABLE)
        clicks()
        return binding.root
    }

    private fun clicks() {
        binding.loginBtn.setOnClickListener {
            if (binding.emailEditText.text.isEmpty()) {
                binding.emailEditText.error = "Email Field is Required"
                Toast.makeText(requireContext(), "Email Field is Required", Toast.LENGTH_SHORT)
                    .show()
            } else if (!Common.isValidEmail(binding.emailEditText.text.toString())) {
                binding.emailEditText.error = "Please Enter Valid Email."
                Toast.makeText(activity, "Please Enter Valid Email.", Toast.LENGTH_SHORT).show()
            } else if (binding.passwordEditText.text.isEmpty()) {
                binding.passwordEditText.error = "Password Field is Required"
                Toast.makeText(requireContext(), "Password Field is Required", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (Common.checkForInternet(requireContext())) {
                    map["u_username"] = binding.emailEditText.text.toString()
                    map["u_password"] = binding.passwordEditText.text.toString()
                    model.loginApi(/*binding.emailEditText.text.toString(),binding.passwordEditText.text.toString()*/
                        map, requireView(), requireContext()
                    ).observe(viewLifecycleOwner, Observer<CommonPojo?> { model ->
                        // update UI

                        Log.e("Login_MESSAGE", model.message.toString())
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
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.signInTextView.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_loginFragment_to_signUpFragment2)

        }
        binding.forgotPasswordTextView.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_loginFragment_to_changePasswordFragment2)
        }
    }
}