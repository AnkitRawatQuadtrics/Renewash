package com.quadtric.renewash.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.quadtric.renewash.R
import com.quadtric.renewash.commonFunctions.SharedPreference
import com.quadtric.renewash.commonFunctions.SharedPreference.Companion.service_id
import com.quadtric.renewash.commonFunctions.SharedPreference.Companion.service_name
import com.quadtric.renewash.commonFunctions.SharedPreference.Companion.total
import com.quadtric.renewash.models.addonModels.AddonData

@Suppress("UNUSED_ANONYMOUS_PARAMETER")
internal class AddonAdapter(
    var context: Context, var view: View,
    private var addonData: List<AddonData>,
    var id: String = "",
    private var ids: StringBuilder = StringBuilder(),
    private val idList: ArrayList<String> = arrayListOf<String>(),
    private var serviceName: String = "",
    private var serviceNameBuilder: StringBuilder = StringBuilder(),
    private val serviceNameList: ArrayList<String> = arrayListOf<String>(),
    private val priceList: ArrayList<Int> = arrayListOf<Int>(),
    private var totalPrice: Int = 0
) :
    RecyclerView.Adapter<AddonAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var addonsCheckbox: CheckBox = view.findViewById(R.id.addonsCheckbox)
        var totalPrice: TextView = view.findViewById(R.id.totalPrice)

    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.addons_layout, parent, false)
        return MyViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.addonsCheckbox.text = addonData[position].serName
        holder.totalPrice.text = addonData[position].serPrice

        if (addonData[position].isChecked) {
            holder.addonsCheckbox.isChecked = true
            for (i in addonData.indices) {
                if (addonData[i].isChecked) {
                    id = addonData[position].serId.toString()
                    serviceName = addonData[position].serName.toString()
                    totalPrice = addonData[position].serPrice!!.toInt()
                }
            }
            priceList.add(totalPrice)
            totalPrice = priceList.sum()
            idList.add(id)
            serviceNameList.add(serviceName)
            ids.append("$id,")
            serviceNameBuilder.append("$serviceName,")
            id = ids.toString().dropLast(1)
            serviceName = serviceNameBuilder.toString().dropLast(1)
            Log.e("selected_id", id)
            Log.e("total_price", totalPrice.toString())
            Log.e("selected_service_name", serviceName)
            SharedPreference.setStringPref(context, service_name, serviceName)
            SharedPreference.setStringPref(context, service_id, id)
            SharedPreference.setStringPref(context, total, totalPrice.toString())
        }
        holder.addonsCheckbox.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                addonData[position].isChecked = true
                for (i in addonData.indices) {
                    if (addonData[i].isChecked) {
                        id = addonData[position].serId.toString()
                        serviceName = addonData[position].serName.toString()
                        totalPrice = addonData[position].serPrice!!.toInt()
                    }
                }
                priceList.add(totalPrice)
                totalPrice = priceList.sum()
                idList.add(id)
                serviceNameList.add(serviceName)
                ids.append("$id,")
                serviceNameBuilder.append("$serviceName,")
                id = ids.toString().dropLast(1)
                serviceName = serviceNameBuilder.toString().dropLast(1)
                Log.e("selected_id", id)
                Log.e("total_price", totalPrice.toString())
                Log.e("selected_service_name", serviceName)
                SharedPreference.setStringPref(context, service_name, serviceName)
                SharedPreference.setStringPref(context, service_id, id)
                SharedPreference.setStringPref(context, total, totalPrice.toString())
            } else {
                addonData[position].isChecked = false
                for (i in addonData.indices) {
                    if (!addonData[i].isChecked) {
                        id = addonData[position].serId.toString()
                        serviceName = addonData[position].serName.toString()
                        totalPrice = addonData[position].serPrice!!.toInt()
                        Log.e("un", id)
                    }
                }
                idList.remove(id)
                serviceNameList.remove(serviceName)
                priceList.remove(totalPrice)
                totalPrice = priceList.sum()
                ids = StringBuilder()
                serviceNameBuilder = StringBuilder()
                for (j in 0 until idList.size) {
                    ids.append(idList[j] + ",")
                }
                id = ids.toString().dropLast(1)
                Log.e("unchecked", id)
                for (j in 0 until serviceNameList.size) {
                    serviceNameBuilder.append(serviceNameList[j] + ",")
                }
                serviceName = serviceNameBuilder.toString().dropLast(1)
                Log.e("unchecked", serviceName)
                Log.e("total_price", totalPrice.toString())
                SharedPreference.setStringPref(context, service_name, serviceName)
                SharedPreference.setStringPref(context, service_id, id)
                SharedPreference.setStringPref(context, total, totalPrice.toString())
            }
            if (SharedPreference.getStringPref(context, "service_id")!!.isNotEmpty()) {
                for (k in 0 until idList.size) {
                    if (idList[k] == addonData[position].serId) {
                        holder.addonsCheckbox.isChecked = true
                    }
                }
            }
        }

    }


    override fun getItemCount(): Int {
        return addonData.size
    }
}

