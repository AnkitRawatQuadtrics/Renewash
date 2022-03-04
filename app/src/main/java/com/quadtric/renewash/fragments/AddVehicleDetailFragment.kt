package com.quadtric.renewash.fragments

import android.content.Context
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
import androidx.navigation.Navigation
import com.quadtric.renewash.R
import com.quadtric.renewash.commonFunctions.Common
import com.quadtric.renewash.commonFunctions.Common.showSummaryDialog
import com.quadtric.renewash.commonFunctions.SharedPreference
import com.quadtric.renewash.commonFunctions.SharedPreference.Companion.getStringPref
import com.quadtric.renewash.commonFunctions.SharedPreference.Companion.plate_number
import com.quadtric.renewash.databinding.FragmentAddVehicleDetailBinding
import com.quadtric.renewash.models.carModel.ModelData
import com.quadtric.renewash.models.carModel.ModelPojo
import com.quadtric.renewash.models.makeByModel.MakeByData
import com.quadtric.renewash.models.makeByModel.MakeByPojo
import com.quadtric.renewash.models.vehicleTypeModel.VehicleTypePojo
import com.quadtric.renewash.viewModels.VehicleInformationViewModel
import com.quadtric.renewash.viewModels.VehicleTypeViewModel


class AddVehicleDetailFragment : Fragment() {
    private lateinit var binding: FragmentAddVehicleDetailBinding
    private val model: VehicleInformationViewModel by viewModels()
    private val modelVehicleType: VehicleTypeViewModel by viewModels()
    private var makeByName: String = ""
    private var modelName: String = ""
    private lateinit var makeById: String
    private lateinit var ctx: Context
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddVehicleDetailBinding.inflate(layoutInflater)
        if (getStringPref(ctx, plate_number) != "") {
            binding.plateNumber.setText(getStringPref(ctx, plate_number).toString())
        }
        click()
        if (Common.checkForInternet(ctx)) {
            model.getMakeBy(ctx).observe(viewLifecycleOwner, Observer<MakeByPojo> { model ->
                // update UI
                binding.makeByTextView.setOnClickListener {
                    showMakeBy(model.data)
                }
            })
        } else {
            Toast.makeText(ctx, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    private fun click() {
        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.previousButton.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.nextButton.setOnClickListener {
            when {
                makeByName.isEmpty() -> {
                    Toast.makeText(activity, "Make by field is required.", Toast.LENGTH_SHORT)
                        .show()
                }
                modelName.isEmpty() -> {
                    Toast.makeText(activity, "Model field is required.", Toast.LENGTH_SHORT).show()
                }
                binding.plateNumber.text.toString().isEmpty() -> {
                    binding.plateNumber.error = "Plate Number field is required."
                }
                else -> {
                    SharedPreference.setStringPref(
                        requireActivity(),
                        plate_number,
                        binding.plateNumber.text.toString()
                    )
                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_addVehicleDetailFragment_to_choosePackageFragment)
                }
            }
        }
        binding.summaryBottomSheet.setOnClickListener {
            showSummaryDialog(
                requireActivity(),
                getStringPref(ctx, SharedPreference.vehicle_type).toString(),
                getStringPref(ctx, SharedPreference.vehicle_package).toString(),
                getStringPref(ctx, SharedPreference.service_name).toString(),
                getStringPref(ctx, SharedPreference.date).toString(),
                getStringPref(ctx, SharedPreference.time).toString(),
                getStringPref(ctx, SharedPreference.total).toString()
            )
        }
    }

    private fun showMakeBy(data: List<MakeByData>?) {
        val popupMenu = PopupMenu(requireContext(), binding.makeByTextView)
        for (i in 0 until data!!.size) {
            popupMenu.menu.add(data[i].name)
        }
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            binding.makeByTextView.text = item.title.toString()
            makeByName = item.title.toString()
            modelVehicleType.getUsers(ctx, makeByName)
                .observe(viewLifecycleOwner, Observer<VehicleTypePojo> { model ->
                    // update UI
                    SharedPreference.setStringPref(
                        requireContext(),
                        SharedPreference.vehicle_type,
                        data[0].name
                    )

                    SharedPreference.setStringPref(
                        requireContext(),
                        SharedPreference.cat_id,
                        data[0].id
                    )
                })
            Log.e("VT_IDDD", getStringPref(requireContext(), SharedPreference.cat_id).toString())
            SharedPreference.setStringPref(
                requireActivity(),
                SharedPreference.make,
                makeByName
            )
            model.getModel(makeByName).observe(viewLifecycleOwner, Observer<ModelPojo> { model ->
                // update UI
                binding.modelTextView.setOnClickListener {
                    showModel(model.data)
                }
            })
            Log.d("make_by_id", item.itemId.toString())
            for (j in data.indices) {
                if (data[j].name!! == item.title) {
                    Log.d("make_by_id", data[j].id.toString())
                    makeById = data[j].id.toString()
                }
            }
            true
        })
        popupMenu.show()

    }

    private fun showModel(data: List<ModelData>?) {
        val popupMenu = PopupMenu(requireContext(), binding.modelTextView)
        for (i in 0 until data!!.size) {
            popupMenu.menu.add(data[i].name)
        }
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            binding.modelTextView.text = item.title.toString()
            modelName = item.title.toString()
            SharedPreference.setStringPref(
                requireActivity(),
                SharedPreference.model,
                modelName
            )
            Log.d("make_by_id", item.itemId.toString())
            true
        })
        popupMenu.show()
    }
}