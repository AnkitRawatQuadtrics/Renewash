package com.quadtric.renewash.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.quadtric.renewash.adapters.VehicleTypeAdapter
import com.quadtric.renewash.commonFunctions.Common
import com.quadtric.renewash.databinding.FragmentVehicleInformationBinding
import com.quadtric.renewash.models.vehicleTypeModel.VehicleTypeData
import com.quadtric.renewash.models.vehicleTypeModel.VehicleTypePojo
import com.quadtric.renewash.viewModels.VehicleTypeViewModel


class VehicleInformationFragment : Fragment() {
    private lateinit var binding: FragmentVehicleInformationBinding
    private val model: VehicleTypeViewModel by viewModels()
    private lateinit var vehicleTypeAdapter: VehicleTypeAdapter
    private lateinit var ctx: Context
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVehicleInformationBinding.inflate(layoutInflater)
        click()
        if (Common.checkForInternet(ctx)) {
            model.getUsers(ctx).observe(viewLifecycleOwner, Observer<VehicleTypePojo> { model ->
                // update UI
                callVehicleAdapter(model.data)
            })
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

    private fun callVehicleAdapter(vehicleTypeData: List<VehicleTypeData>?) {
        vehicleTypeAdapter = VehicleTypeAdapter(ctx, requireView(), vehicleTypeData!!)
        val layoutManager = LinearLayoutManager(ctx)
        binding.vehicleTypeRv.layoutManager = layoutManager
        binding.vehicleTypeRv.itemAnimator = DefaultItemAnimator()
        binding.vehicleTypeRv.adapter = vehicleTypeAdapter
    }

    private fun click() {
        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}