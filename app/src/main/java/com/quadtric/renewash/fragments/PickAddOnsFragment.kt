package com.quadtric.renewash.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.quadtric.renewash.R
import com.quadtric.renewash.adapters.AddonAdapter
import com.quadtric.renewash.adapters.SubscriptionAdapter
import com.quadtric.renewash.commonFunctions.Common
import com.quadtric.renewash.commonFunctions.SharedPreference
import com.quadtric.renewash.databinding.FragmentPickAddOnsBinding
import com.quadtric.renewash.models.addonModels.AddonData
import com.quadtric.renewash.models.addonModels.AddonPojo
import com.quadtric.renewash.models.subscriptionModels.SubscriptionData
import com.quadtric.renewash.models.subscriptionModels.SubscriptionPojo
import com.quadtric.renewash.viewModels.AddonsViewModel


class PickAddOnsFragment : Fragment() {
    private lateinit var binding: FragmentPickAddOnsBinding
    private lateinit var addonAdapter: AddonAdapter
    private lateinit var subscriptionAdapter: SubscriptionAdapter
    private val model: AddonsViewModel by viewModels()
    private lateinit var ctx: Context
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPickAddOnsBinding.inflate(layoutInflater)
        click()
        SharedPreference.setStringPref(
            ctx,
            SharedPreference.date,
            ""
        )
        val id = arguments?.getString("id").toString()
        Log.e("ID", id)
        if (Common.checkForInternet(ctx)) {
            if (SharedPreference.getStringPref(requireContext(),SharedPreference.qt_type) != "1") {
                binding.headerTextView.text = "Pick Addons"
                binding.pickTv.text= "Pick Addons"
                binding.buttonsLinearLayout.visibility= View.VISIBLE
                model.getAddons(id, ctx).observe(viewLifecycleOwner, Observer<AddonPojo> { model ->
                    // update UI
                    callAddonAdapter(model.data)
                })

            } else {
                binding.buttonsLinearLayout.visibility= View.GONE
                binding.headerTextView.text = "Pick Subscription"
                binding.pickTv.text= "Pick Subscription"
                model.getSubscription(id, ctx)
                    .observe(viewLifecycleOwner, Observer<SubscriptionPojo> { model ->
                        // update UI
                        callSubscriptionAdapter(model.data)
                    })
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Please Check Your Internet Connection.",
                Toast.LENGTH_SHORT
            ).show()
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    private fun callAddonAdapter(addonData: List<AddonData>?) {
        addonAdapter = AddonAdapter(ctx, requireView(), addonData!!)
        val layoutManager = LinearLayoutManager(ctx)
        binding.addonRecyclerView.layoutManager = layoutManager
        binding.addonRecyclerView.itemAnimator = DefaultItemAnimator()
        binding.addonRecyclerView.adapter = addonAdapter
    }

    private fun callSubscriptionAdapter(subscriptionData: List<SubscriptionData>?) {
        subscriptionAdapter = SubscriptionAdapter(ctx,requireView(), subscriptionData!!)
        val layoutManager = LinearLayoutManager(ctx)
        binding.addonRecyclerView.layoutManager = layoutManager
        binding.addonRecyclerView.itemAnimator = DefaultItemAnimator()
        binding.addonRecyclerView.adapter = subscriptionAdapter
    }

    private fun click() {
        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.previousButton.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.nextButton.setOnClickListener {
            if (SharedPreference.getStringPref(ctx, "service_id")!!.isEmpty()) {
                Toast.makeText(ctx, "Select Addon", Toast.LENGTH_SHORT).show()
            } else {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_pickAddOnsFragment_to_carWashDateTimeFragment)
            }
        }
        binding.summaryBottomSheet.setOnClickListener {
            Common.showSummaryDialog(
                requireActivity(),
                SharedPreference.getStringPref(ctx, SharedPreference.vehicle_type).toString(),
                SharedPreference.getStringPref(ctx, SharedPreference.vehicle_package).toString(),
                SharedPreference.getStringPref(ctx, SharedPreference.service_name).toString(),
                SharedPreference.getStringPref(ctx, SharedPreference.date).toString(),
                SharedPreference.getStringPref(ctx, SharedPreference.time).toString(),
                SharedPreference.getStringPref(ctx, SharedPreference.total).toString()
            )
        }
    }


}