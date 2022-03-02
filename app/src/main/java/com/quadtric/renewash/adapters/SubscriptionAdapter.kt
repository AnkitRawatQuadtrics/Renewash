package com.quadtric.renewash.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.quadtric.renewash.R
import com.quadtric.renewash.commonFunctions.SharedPreference
import com.quadtric.renewash.models.subscriptionModels.SubscriptionData

internal class SubscriptionAdapter(
    var context: Context,
    val view: View,
    private var subscriptionData: List<SubscriptionData>

) :
    RecyclerView.Adapter<SubscriptionAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var subscriptionName: TextView = view.findViewById(R.id.subscriptionName)
        var subscriptionLinear: LinearLayout = view.findViewById(R.id.subscriptionLinear)

    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.subscription_layout, parent, false)
        return MyViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.subscriptionName.text = subscriptionData[position].subscriptionName
        holder.subscriptionLinear.setOnClickListener {
            SharedPreference.setStringPref(
                context,
                SharedPreference.subscription_price,
                subscriptionData[position].price
            )
            SharedPreference.setStringPref(
                context,
                SharedPreference.subscription_name,
                subscriptionData[position].subscriptionName
            )
            Navigation.findNavController(view)
                .navigate(
                    R.id.action_pickAddOnsFragment_to_selectSubscriptionTime
                )
        }
    }


    override fun getItemCount(): Int {
        return subscriptionData.size
    }
}