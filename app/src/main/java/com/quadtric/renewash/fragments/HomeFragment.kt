package com.quadtric.renewash.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.quadtric.renewash.R
import com.quadtric.renewash.commonFunctions.Common
import com.quadtric.renewash.commonFunctions.SharedPreference
import com.quadtric.renewash.commonFunctions.SharedPreference.Companion.PREF_NAME
import com.quadtric.renewash.databinding.FragmentHomeBinding


@Suppress("UNUSED_ANONYMOUS_PARAMETER")
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        clicks()
        clearSharedPrefws()
        if (SharedPreference.getStringPref(requireContext(),SharedPreference.user_id)!!.isEmpty()){
            binding.loginTextView.text = getString(R.string.login_now)
        }else{
            binding.loginTextView.text = getString(R.string.log_out)
        }
        return binding.root
    }

    private fun clearSharedPref() {
        val settings = requireContext()
            .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        settings.edit().clear().apply()
    }

    private fun clearSharedPrefws() {
        SharedPreference.setStringPref(requireContext(),SharedPreference.vehicle_type,"")
        SharedPreference.setStringPref(requireContext(),SharedPreference.cat_id,"")
        SharedPreference.setStringPref(requireContext(),SharedPreference.vehicle_package,"")
        SharedPreference.setStringPref(requireContext(),SharedPreference.date,"")
        SharedPreference.setStringPref(requireContext(),SharedPreference.time,"")
        SharedPreference.setStringPref(requireContext(),SharedPreference.date_time,"")
        SharedPreference.setStringPref(requireContext(),SharedPreference.coupon_price,"")
        SharedPreference.setStringPref(requireContext(),SharedPreference.final_mount,"")
        SharedPreference.setStringPref(requireContext(),SharedPreference.gift_no,"")
        SharedPreference.setStringPref(requireContext(),SharedPreference.gift_amount,"")
        SharedPreference.setStringPref(requireContext(),SharedPreference.make,"")
        SharedPreference.setStringPref(requireContext(),SharedPreference.model,"")
        SharedPreference.setStringPref(requireContext(),SharedPreference.plate_number,"")
        SharedPreference.setStringPref(requireContext(),SharedPreference.qt_type,"")
        SharedPreference.setStringPref(requireContext(),SharedPreference.service_id,"")
        SharedPreference.setStringPref(requireContext(),SharedPreference.package_id,"")
        SharedPreference.setStringPref(requireContext(),SharedPreference.service_name,"")
        SharedPreference.setStringPref(requireContext(),SharedPreference.subscription_name,"")
        SharedPreference.setStringPref(requireContext(),SharedPreference.subscription_price,"")
    }
    @SuppressLint("InflateParams")
    fun alertLogOutDialog() {
        var dialog: Dialog? = null
        try {
            dialog?.dismiss()
            dialog = Dialog(requireContext(), R.style.AppCompatAlertDialogStyleBig)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            val m_inflater = LayoutInflater.from(requireContext())
            val m_view = m_inflater.inflate(R.layout.dlg_logout, null, false)

            val tvLogout: TextView = m_view.findViewById(R.id.tvLogout)
            val tvCancel: TextView = m_view.findViewById(R.id.tvCancel)
            val finalDialog: Dialog = dialog
            tvLogout.setOnClickListener {
                if (Common.checkForInternet(requireContext())) {
                    finalDialog.dismiss()
                   clearSharedPref()
                    requireActivity().onBackPressed()
                }/* else {
                    
                }*/
            }
            tvCancel.setOnClickListener {
                finalDialog.dismiss()
            }
            dialog.setContentView(m_view)
            dialog.show()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
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
        binding.loginTextView.setOnClickListener {
            if (SharedPreference.getStringPref(requireContext(),SharedPreference.user_id)!!.isEmpty()){
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_homeFragment_to_loginFragment)
            }else{
               alertLogOutDialog()
            }
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