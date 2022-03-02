package com.quadtric.renewash.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.quadtric.renewash.R
import com.quadtric.renewash.commonFunctions.SharedPreference
import com.quadtric.renewash.models.packageModel.PackageData
import java.lang.StringBuilder

internal class PackageAdapter(
    var context: Context, var view: View,
    private var packageData: List<PackageData>
) :
    RecyclerView.Adapter<PackageAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var packageName: TextView = view.findViewById(R.id.packageName)
        var servicesTextView: TextView = view.findViewById(R.id.servicesTextView)
        var packageLayout: RelativeLayout = view.findViewById(R.id.interiorOnlyRelativeOut)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.packages_layout, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val serviceBuilder = StringBuilder()
        for (i in 0 until packageData[position].serviceList.size) {
            serviceBuilder.append(packageData[position].serviceList[i].serName + ",")
        }
        val services: String = serviceBuilder.toString().dropLast(1)
        holder.servicesTextView.text = services
        holder.packageName.text = packageData[position].pName

        holder.packageLayout.setOnClickListener {
            SharedPreference.setStringPref(
                context,
                SharedPreference.vehicle_package,
                packageData[position].pName
            )
            SharedPreference.setStringPref(
                context,
                SharedPreference.package_id,
                packageData[position].pId
            )
            val bundle = bundleOf("id" to packageData[position].pId)
            Navigation.findNavController(view)
                .navigate(
                    R.id.action_choosePackageFragment_to_pickAddOnsFragment,bundle
                )
        }

    }

    override fun getItemCount(): Int {
        return packageData.size
    }
}
