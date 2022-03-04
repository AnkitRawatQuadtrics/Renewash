package com.quadtric.renewash.fragments

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.text.InputFilter
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
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.quadtric.renewash.R
import com.quadtric.renewash.adapters.PlacesAdapter
import com.quadtric.renewash.commonFunctions.Common
import com.quadtric.renewash.commonFunctions.SharedPreference
import com.quadtric.renewash.databinding.FragmentFillInformationBinding
import com.quadtric.renewash.models.stateModels.StateData
import com.quadtric.renewash.models.stateModels.StatePojo
import com.quadtric.renewash.viewModels.StateViewModel
import java.io.IOException
import java.util.*


@Suppress("UNUSED_ANONYMOUS_PARAMETER", "SpellCheckingInspection")
class FillInformationFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentFillInformationBinding
    private val model: StateViewModel by viewModels()
    private lateinit var state: String
    private lateinit var placeAdapter: PlacesAdapter
    private lateinit var mPlacesClient: PlacesClient
    private lateinit var pickedLocation: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFillInformationBinding.inflate(layoutInflater)
        click()
        Places.initialize(requireContext(), getString(R.string.api_key))
        mPlacesClient = Places.createClient(requireContext())
        ignoreSpecialChars()
//        init()
        return binding.root
    }

    private fun ignoreSpecialChars() {
        val filter =
            InputFilter { source, start, end, dest, dstart, dend ->
                for (i in start until end) {
                    if (!Character.isLetterOrDigit(source[i])) {
                        return@InputFilter ""
                    }
                }
                null
            }
        binding.phone.filters = arrayOf(filter, InputFilter.LengthFilter(15))
    }

/*
    private fun showStates(data: List<StateData>?) {
        val popupMenu = PopupMenu(requireContext(), binding.state)
        for (i in 0 until data!!.size) {
            popupMenu.menu.add(data[i].name)
        }
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            binding.state.text = item.title.toString()
            state = item.title.toString()
            true
        })
        popupMenu.show()
    }
*/

    private fun initializeAddress() {
        Places.initialize(requireContext(), getString(R.string.api_key))
        val placesClient = Places.createClient(requireContext())
        val fields: List<Place.Field> =
            listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
        val intent = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.FULLSCREEN, fields
        )
            .build(requireContext())
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data!!)
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == 1) {
                when (resultCode) {
                    RESULT_OK -> {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        binding.address.text = place.name
                        val latLng = place.latLng
                        val myLat = latLng?.latitude
                        val myLong = latLng?.longitude
                        val geocoder = Geocoder(requireActivity(), Locale.getDefault())
                        try {
                            val addresses: List<Address> =
                                geocoder.getFromLocation(myLat!!, myLong!!, 1)
                            var stateName = ""
                            var cityName = ""
                            var zipCode = ""
                            when {
                                addresses[0].adminArea != null -> {
                                    stateName = addresses[0].adminArea
                                }
                                addresses[0].locality != null -> {
                                    cityName = addresses[0].locality
                                }
                                addresses[0].postalCode != null -> {
                                    zipCode = addresses[0].postalCode
                                }
                            }
                            binding.city.setText(cityName)
                            binding.state.setText(stateName)
                            binding.zipCode.setText(zipCode)
                            Log.e(TAG, "CITY: $cityName")
                            Log.e(TAG, "STATE: $stateName")
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        Log.e(TAG, "Place: " + place.name + ", " + place.id)

                    }
                    AutocompleteActivity.RESULT_ERROR -> {
                        // TODO: Handle the error.
                        val status: Status = Autocomplete.getStatusFromIntent(data)
                        Log.i(TAG, status.statusMessage!!)
                    }
                    RESULT_CANCELED -> {
                        // The user canceled the operation.
                    }
                }
            }
        }
    }

/*
    private fun init() {
        val apiKey = getString(R.string.api_key)
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), apiKey)
        }
        placeAdapter = PlacesAdapter(requireContext()) { prediction ->
            pickedLocation = prediction.toString()
            binding.address.setText(prediction.getFullText(null).toString())
            binding.placesRecyclerView.visibility = View.GONE
        }
        binding.placesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.placesRecyclerView.adapter = placeAdapter
        binding.address.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(text: Editable?) {
                binding.placesRecyclerView.visibility = View.VISIBLE
                if (text.toString().isNotEmpty()) {
                    placeAdapter.filter.filter(text.toString())
                    binding.placesRecyclerView.visibility = View.VISIBLE
                    */
/*  if (binding.placesRecyclerView.isGone) {
                          binding.placesRecyclerView.visibility = View.VISIBLE
                      }*//*

                } else {
                    binding.placesRecyclerView.visibility = View.GONE
                    */
/* if (binding.placesRecyclerView.isVisible) {
                         binding.placesRecyclerView.visibility = View.GONE
                     }*//*

                }
            }
        })
    }
*/


    private fun click() {
        binding.address.setOnClickListener {
            initializeAddress()
        }


        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.previousButton.setOnClickListener {
            activity?.onBackPressed()
        }
/*
        model.getStates(requireContext())
            .observe(viewLifecycleOwner, Observer<StatePojo?> { model ->
                // update UI
                binding.state.setOnClickListener {
                    showStates(model.data)
                }
            })
*/


        binding.nextButton.setOnClickListener {
            when {
                binding.firstName.text.toString().isEmpty() -> {
                    binding.firstName.error = "First Name field is required."
                    Toast.makeText(activity, "First Name field is required.", Toast.LENGTH_SHORT)
                        .show()
                }
                binding.lastName.text.toString().isEmpty() -> {
                    binding.lastName.error = "Last Name is required."
                    Toast.makeText(activity, "Last Name is required.", Toast.LENGTH_SHORT).show()
                }
                binding.email.text.toString().isEmpty() -> {
                    binding.email.error = "Email field is required."
                    Toast.makeText(activity, "Email field is required.", Toast.LENGTH_SHORT).show()
                }
                !Common.isValidEmail(binding.email.text.toString()) -> {
                    binding.email.error = "Please Enter Valid Email."
                    Toast.makeText(activity, "Please Enter Valid Email.", Toast.LENGTH_SHORT)
                        .show()
                }
                binding.phone.text.toString().isEmpty() -> {
                    binding.phone.error = "Phone Number field is required."
                    Toast.makeText(activity, "Phone Number field is required.", Toast.LENGTH_SHORT)
                        .show()
                }
                binding.phone.text.toString().length < 9 -> {
                    binding.phone.error = "Please Enter Valid Phone Number"
                    Toast.makeText(activity, "Please Enter Valid Phone Number.", Toast.LENGTH_SHORT)
                        .show()
                }
                binding.address.text.toString().isEmpty() -> {
                    binding.address.error = "Address field is required."
                    Toast.makeText(activity, "Address field is required.", Toast.LENGTH_SHORT)
                        .show()
                }
                !binding.termsAndCondition.isChecked -> {
                    Toast.makeText(requireContext(), "Agree Terms & Conditions", Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    val bundle = Bundle()
                    bundle.putString("first_name", binding.firstName.text.toString())
                    bundle.putString("last_name", binding.lastName.text.toString())
                    bundle.putString("email", binding.email.text.toString())
                    bundle.putString("password", binding.password.text.toString())
                    bundle.putString("phone_number", binding.phone.text.toString())
                    bundle.putString("address", binding.address.text.toString())
                    bundle.putString("city", binding.city.text.toString())
                    bundle.putString("state", binding.state.text.toString())
                    bundle.putString("zip_code", binding.zipCode.text.toString())
                    bundle.putString("message", binding.additionalInfo.text.toString())
                    Navigation.findNavController(requireView())
                        .navigate(
                            R.id.action_fillInformationFragment_to_paymentOptionFragment, bundle
                        )
                }

            }
        }
        binding.summaryBottomSheet.setOnClickListener {
            Common.showSummaryDialog(
                requireActivity(), SharedPreference.getStringPref(
                    requireActivity(),
                    SharedPreference.vehicle_type
                ).toString(),
                SharedPreference.getStringPref(
                    requireActivity(), SharedPreference.vehicle_package
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

    override fun onMapReady(p0: GoogleMap) {
        TODO("Not yet implemented")
    }
}