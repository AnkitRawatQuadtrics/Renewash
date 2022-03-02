package com.quadtric.renewash.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.quadtric.renewash.commonFunctions.Common
import com.quadtric.renewash.databinding.FragmentGiftCardBinding
import com.quadtric.renewash.models.fillinformationModels.CommonPojo
import com.quadtric.renewash.viewModels.PurchaseGiftCardViewModel


class GiftCardFragment : Fragment() {
    private lateinit var binding: FragmentGiftCardBinding
    private lateinit var giftAmount: String
    private var map: HashMap<String, String> = HashMap()
    private val model: PurchaseGiftCardViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGiftCardBinding.inflate(layoutInflater)
        click()
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        click()
    }

    private fun showAmount() {
        val popupMenu = PopupMenu(requireContext(), binding.amountTextView)
        val data: MutableList<String> = ArrayList()
        data.add("100")
        data.add("200")
        data.add("500")
        data.add("Custom")
        for (i in 0 until data.size) {
            popupMenu.menu.add(data[i])
        }
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            binding.amountTextView.text = item.title.toString()
            giftAmount = item.title.toString()
            if (giftAmount == "Custom") {
                binding.enterAmountLinear.visibility = View.VISIBLE
            } else {
                binding.enterAmountLinear.visibility = View.GONE
            }
            Log.d("giftAmountID", item.itemId.toString())
            Log.d("giftAmount", giftAmount)
            true
        })
        popupMenu.show()

    }

    private fun click() {
        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.previousButton.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.amountTextView.setOnClickListener {
            showAmount()
        }
        binding.submitButton.setOnClickListener {
            when {
                binding.amountTextView.text.toString().isEmpty() -> {
                    binding.amountTextView.error = "Amount field is required."
                    Toast.makeText(activity, "Amount field is required.", Toast.LENGTH_SHORT).show()
                }
                binding.yourName.text.toString().isEmpty() -> {
                    binding.yourName.error = "Your Name field is required."
                    Toast.makeText(activity, "Your Name field is required.", Toast.LENGTH_SHORT)
                        .show()
                }
                binding.yourEmail.text.toString().isEmpty() -> {
                    binding.yourEmail.error = "Your Email field is required."
                    Toast.makeText(activity, "Your Email field is required.", Toast.LENGTH_SHORT)
                        .show()
                }
                !Common.isValidEmail(binding.yourEmail.text.toString()) -> {
                    binding.yourEmail.error = "Please Enter Valid Email."
                    Toast.makeText(activity, "Please Enter Valid Email.", Toast.LENGTH_SHORT).show()
                }
                binding.recipientsName.text.toString().isEmpty() -> {
                    binding.recipientsName.error = "Recipients Name is required."
                    Toast.makeText(activity, "Recipients Name is required.", Toast.LENGTH_SHORT)
                        .show()
                }
                binding.recipientsEmail.text.toString().isEmpty() -> {
                    binding.recipientsEmail.error = "Recipients Email field is required."
                    Toast.makeText(
                        activity,
                        "Recipients Email field is required.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                !Common.isValidEmail(binding.recipientsEmail.text.toString()) -> {
                    binding.recipientsEmail.error = "Please Enter Valid Recipients Email."
                    Toast.makeText(
                        activity,
                        "Please Enter Valid Recipients Email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                binding.specialMessage.text.toString().isEmpty() -> {
                    binding.specialMessage.error = "Special Message field is required."
                    Toast.makeText(
                        activity,
                        "Special Message field is required.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                binding.cardNumber.text.toString().isEmpty() -> {
                    binding.cardNumber.error = "Card Number field is required."
                    Toast.makeText(activity, "Card Number field is required.", Toast.LENGTH_SHORT)
                        .show()
                }
                binding.expiryMonth.text.toString().isEmpty() -> {
                    binding.expiryMonth.error = "Expiry Month field is required."
                    Toast.makeText(activity, "Expiry Month field is required.", Toast.LENGTH_SHORT)
                        .show()
                }
                binding.expiryYear.text.toString().isEmpty() -> {
                    binding.expiryYear.error = "Expiry Year field is required."
                    Toast.makeText(activity, "Expiry Year field is required.", Toast.LENGTH_SHORT)
                        .show()
                }
                binding.cvv.text.toString().isEmpty() -> {
                    binding.cvv.error = "Cvv field is required."
                    Toast.makeText(activity, "Cvv field is required.", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    if (giftAmount == "Custom") {
                        map["purchase_amount"] = binding.enterAmount.text.toString()
                    } else {
                        map["purchase_amount"] = giftAmount
                    }
                    map["user_name"] = binding.yourName.text.toString()
                    map["user_email"] = binding.yourEmail.text.toString()
                    map["recipient_name"] = binding.recipientsName.text.toString()
                    map["recipient_email"] = binding.recipientsEmail.text.toString()
                    map["special_message"] = binding.specialMessage.text.toString()
                    map["xCardNum"] = binding.cardNumber.text.toString()
                    map["xExpM"] = binding.expiryMonth.text.toString()
                    map["xExpY"] = binding.expiryYear.text.toString()
                    map["xCVV"] = binding.cvv.text.toString()
                    if (Common.checkForInternet(requireContext())) {
                        model.purchaseGift(
                            requireContext(),
                            map
                        ).observe(viewLifecycleOwner, Observer<CommonPojo> { model ->
                            // update UI
                            Log.e("PAYMENT_DATA", model.message.toString())
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