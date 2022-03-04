package com.quadtric.renewash.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.quadtric.renewash.R
import com.quadtric.renewash.commonFunctions.Common
import com.quadtric.renewash.commonFunctions.SharedPreference
import com.quadtric.renewash.models.vehicleTypeModel.VehicleTypeData


internal class VehicleTypeAdapter(
    var context: Context, var view: View,
    private var vehicleTypeData: List<VehicleTypeData>
) :
    RecyclerView.Adapter<VehicleTypeAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var vehicleName: TextView = view.findViewById(R.id.vehicle_name)
        var vehicleLinearLayout: LinearLayout = view.findViewById(R.id.vehicleLinearLayout)
        var vehicleImage: ImageView = view.findViewById(R.id.vehicle_image)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.vehicle_type_layout, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.vehicleName.text = vehicleTypeData[position].vtName

        holder.vehicleLinearLayout.setOnClickListener {
            SharedPreference.setStringPref(
                context,
                SharedPreference.vehicle_type,
                vehicleTypeData[position].vtName
            )

            SharedPreference.setStringPref(
                context,
                SharedPreference.cat_id,
                vehicleTypeData[position].vtId
            )
          /*  Navigation.findNavController(view)
                .navigate(R.id.action_vehicleInformationFragment_to_addVehicleDetailFragment)*/
        }
        Glide.with(context)
            .load(Common.BaseImageUrl + vehicleTypeData[position].vtImage)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.car)
                    .fitCenter()
            )
            .into(holder.vehicleImage)
    }

    override fun getItemCount(): Int {
        return vehicleTypeData.size
    }
}