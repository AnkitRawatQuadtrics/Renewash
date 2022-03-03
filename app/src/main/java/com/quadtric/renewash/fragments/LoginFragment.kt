package com.quadtric.renewash.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.quadtric.renewash.R
import com.quadtric.renewash.databinding.FragmentHomeBinding
import com.quadtric.renewash.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        clicks()
        return binding.root
    }

    private fun clicks() {
        binding.loginBtn.setOnClickListener {
            requireActivity().onBackPressed()
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