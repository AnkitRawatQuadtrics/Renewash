package com.quadtric.renewash.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.quadtric.renewash.adapters.PackageAdapter
import com.quadtric.renewash.commonFunctions.Common
import com.quadtric.renewash.commonFunctions.SharedPreference
import com.quadtric.renewash.databinding.FragmentChoosePackageBinding
import com.quadtric.renewash.models.packageModel.PackageData
import com.quadtric.renewash.models.packageModel.PackagePojo
import com.quadtric.renewash.viewModels.PackagesViewModel

class ChoosePackageFragment : Fragment() {
    private lateinit var binding: FragmentChoosePackageBinding
    private lateinit var packageAdapter: PackageAdapter
    private val model: PackagesViewModel by viewModels()
    private lateinit var ctx: Context
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChoosePackageBinding.inflate(layoutInflater)
        if (Common.checkForInternet(requireContext())) {
            model.getPackages(
                ctx,
                SharedPreference.getStringPref(requireContext(), SharedPreference.cat_id).toString()
            ).observe(viewLifecycleOwner, Observer<PackagePojo> { model ->
                // update UI
                if (model.data.isNullOrEmpty()) {
                    requireActivity().onBackPressed()
                    Toast.makeText(ctx, "Package Not Available.", Toast.LENGTH_SHORT).show()
                }
                callPackageAdapter(model.data)
            })
        } else {
            Toast.makeText(ctx, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show()
        }
        click()
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    private fun callPackageAdapter(packageData: List<PackageData>?) {
        packageAdapter = PackageAdapter(ctx, requireView(), packageData!!)
        val layoutManager = LinearLayoutManager(ctx)
        binding.packagesRecyclerView.layoutManager = layoutManager
        binding.packagesRecyclerView.itemAnimator = DefaultItemAnimator()
        binding.packagesRecyclerView.adapter = packageAdapter
    }

    private fun click() {
        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }
        /* binding.interiorOnlyRelativeOut.setOnClickListener {
             Navigation.findNavController(requireView())
                 .navigate(R.id.action_choosePackageFragment_to_pickAddOnsFragment)
         }*/
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