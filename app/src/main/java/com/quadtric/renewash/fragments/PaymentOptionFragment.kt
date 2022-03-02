package com.quadtric.renewash.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.quadtric.renewash.commonFunctions.Common
import com.quadtric.renewash.commonFunctions.SharedPreference
import com.quadtric.renewash.databinding.FragmentPaymentOptionBinding
import com.quadtric.renewash.models.applyCouponModel.ApplyCouponPojo
import com.quadtric.renewash.models.fillinformationModels.CommonPojo
import com.quadtric.renewash.models.pamentTypeModel.PaymentTypeData
import com.quadtric.renewash.models.pamentTypeModel.PaymentTypePojo
import com.quadtric.renewash.viewModels.FillInformationViewModel
import com.quadtric.renewash.viewModels.PaymentTypeViewModels


class PaymentOptionFragment : Fragment() {
    private lateinit var binding: FragmentPaymentOptionBinding
    private val model: FillInformationViewModel by viewModels()
    private val modelPayment: PaymentTypeViewModels by viewModels()
    private var paymentType: String = ""
    private var couponCode: String = ""
    private var finalAMOUNT: Int = 0
    private var giftCardBuilder: StringBuilder = StringBuilder()
    private lateinit var paymentTypeId: String
    private var map: HashMap<String, String> = HashMap()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentOptionBinding.inflate(layoutInflater)
        click()
        Log.e("DATA", requireArguments().getString("first_name")!!)
        Log.e("DATA", requireArguments().getString("email")!!)
        Log.e("DATA", requireArguments().getString("message")!!)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        click()
    }

    private fun showPaymentTypes(data: List<PaymentTypeData>?) {
        val popupMenu = PopupMenu(requireContext(), binding.paymentType)
        for (i in 0 until data!!.size) {
            popupMenu.menu.add(data[i].psType)
        }
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            binding.paymentType.text = item.title.toString()
            paymentType = item.title.toString()
            when {
                paymentType.isEmpty() -> {
                    Toast.makeText(requireContext(), "Payment Type Is required", Toast.LENGTH_SHORT)
                        .show()
                }
                paymentType == "cash" -> {
                    binding.cardInformationLayout.visibility = View.GONE
                }
                paymentType == "cardknox" -> {
                    binding.cardInformationLayout.visibility = View.VISIBLE
                }
                else -> {
                    binding.cardInformationLayout.visibility = View.VISIBLE
                }
            }
            Log.d("payment_type_id", item.itemId.toString())
            Log.d("payment_type", paymentType)
            for (j in data.indices) {
                if (data[j].psType!! == item.title) {
                    Log.d("make_by_id", data[j].psId.toString())
                    paymentTypeId = data[j].psId.toString()
                }
            }
            true
        })
        popupMenu.show()

    }

    private fun addDataInHasMap() {
        map["bk_fname"] = requireArguments().getString("first_name").toString()
        map["bk_lname"] = requireArguments().getString("last_name").toString()
        map["bk_email"] = requireArguments().getString("email").toString()
        map["bk_phone"] = requireArguments().getString("phone_number").toString()
        map["bk_message"] = requireArguments().getString("message").toString()
        map["bk_address"] = requireArguments().getString("address").toString()
        map["dates"] = SharedPreference.getStringPref(requireContext(), "date_time").toString()
        map["bk_cat_id"] = SharedPreference.getStringPref(requireContext(), "cat_id").toString()
        map["bk_ser_id"] = SharedPreference.getStringPref(requireContext(), "service_id").toString()
        map["bk_cat_name"] =
            SharedPreference.getStringPref(requireContext(), "vehicle_type").toString()
        map["bk_ser_name"] =
            SharedPreference.getStringPref(requireContext(), "service_name").toString()
        map["bk_amt"] = SharedPreference.getStringPref(requireContext(), "total").toString()
        map["gift_amount"] =
            SharedPreference.getStringPref(requireContext(), "gift_amount").toString()
        map["gift_no"] = SharedPreference.getStringPref(requireContext(), "gift_no").toString()
        map["bk_pkage_name"] =
            SharedPreference.getStringPref(requireContext(), "vehicle_package").toString()
        map["make"] = SharedPreference.getStringPref(requireContext(), "make").toString()
        map["model"] = SharedPreference.getStringPref(requireContext(), "model").toString()
        map["plate_number"] =
            SharedPreference.getStringPref(requireContext(), "plate_number").toString()
        map["qt_Type"] = "1"
        map["pgmethod"] = paymentType
        map["alldata"] = ""
        map["xCardNum"] = binding.cardNumber.text.toString()
        map["xExpM"] = binding.expiryMonth.text.toString()
        map["xExpY"] = binding.expiryYear.text.toString()
        map["xCVV"] = binding.cvv.text.toString()
    }

    private fun click() {
        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.previousButton.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.applyCoupon.setOnClickListener {
            Toast.makeText(requireContext(), "Coming Soon!!", Toast.LENGTH_SHORT).show()

            /* if (binding.giftCardNumber.text.toString().isEmpty()) {
                 Toast.makeText(requireContext(), "Enter Gift Card", Toast.LENGTH_SHORT)
                     .show()
             } else {
                 giftCardBuilder.append(binding.giftCardNumber.text.toString() + ",")
                 couponCode = giftCardBuilder.toString().dropLast(1)
                 model.applyCoupon(
                     couponCode,
                     requireContext(),
                     requireView()
                 ).observe(viewLifecycleOwner, Observer<ApplyCouponPojo> { model ->
                     // update UI
                     if (model.data.size!=0) {
                         for (i in 0 until model.data.size) {
                             SharedPreference.setStringPref(
                                 requireContext(),
                                 SharedPreference.coupon_price,
                                 model.data[i].purchase_amount
                             )
                             SharedPreference.setStringPref(
                                 requireContext(),
                                 SharedPreference.gift_amount,
                                 model.data[i].purchase_amount
                             )
                             SharedPreference.setStringPref(
                                 requireContext(),
                                 SharedPreference.gift_no,
                                 model.data[i].gift_no
                             )
                         }
                         finalAMOUNT = SharedPreference.getStringPref(requireContext(), "total")!!
                             .toInt() - SharedPreference.getStringPref(
                             requireContext(),
                             "coupon_price"
                         )!!
                             .toInt()
                         SharedPreference.setStringPref(
                             requireContext(),
                             SharedPreference.total,
                             finalAMOUNT.toString()
                         )
                         Log.e("finalAMOUNT", finalAMOUNT.toString())
                         Log.e("PAYMENT_DATA", model.message.toString())
                     }else{
                         Toast.makeText(requireContext(),"Coupon Code is Expired",Toast.LENGTH_SHORT).show()
                     }
                 })
             }*/
        }
        binding.submitButton.setOnClickListener {
            if (Common.checkForInternet(requireContext())) {
                when {
                    paymentType.isEmpty() -> {
                        Toast.makeText(requireContext(), "Select Payment Type", Toast.LENGTH_SHORT)
                            .show()
                    }
                    paymentType != "cash" -> {
                        when {
                            binding.cardNumber.text.toString().isEmpty() -> {
                                Toast.makeText(
                                    requireContext(),
                                    "Card Number Required",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                            binding.expiryMonth.text.toString().isEmpty() -> {
                                Toast.makeText(
                                    requireContext(),
                                    "Expiry Month Required",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                            binding.expiryYear.text.toString().isEmpty() -> {
                                Toast.makeText(
                                    requireContext(),
                                    "Expiry Year Required",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                            binding.cvv.text.toString().isEmpty() -> {
                                Toast.makeText(requireContext(), "CVV Required", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            else -> {
                                addDataInHasMap()

                                model.bookAppointment(
                                    map,
                                    requireContext(),
                                    requireView()
                                ).observe(viewLifecycleOwner, Observer<CommonPojo> { model ->
                                    // update UI
                                    Log.e("PAYMENT_DATA", model.message.toString())
                                })
                            }
                        }

                    }
                    else -> {
                        addDataInHasMap()
                        model.bookAppointment(
                            map,
                            requireContext(),
                            requireView()
                        ).observe(viewLifecycleOwner, Observer<CommonPojo> { model ->
                            // update UI
                            Log.e("PAYMENT_DATA", model.message.toString())
                        })
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please Check Your Internet Connection.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.paymentType.setOnClickListener {
            modelPayment.getPaymentType(requireContext())
                .observe(viewLifecycleOwner, Observer<PaymentTypePojo> { model ->
                    // update UI
                    showPaymentTypes(model.data)
                })
        }
        binding.summaryBottomSheet.setOnClickListener {
            Common.showSummaryDialog(
                requireActivity(),
                SharedPreference.getStringPref(requireActivity(), SharedPreference.vehicle_type)
                    .toString(),
                SharedPreference.getStringPref(
                    requireActivity(),
                    SharedPreference.vehicle_package
                ).toString(),
                SharedPreference.getStringPref(requireActivity(), SharedPreference.service_name)
                    .toString(),
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