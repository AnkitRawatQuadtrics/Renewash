package com.quadtric.renewash.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.quadtric.renewash.R
import com.quadtric.renewash.commonFunctions.SharedPreference
import com.quadtric.renewash.models.getCardModel.GetCardData

internal class CardAdapter(
    var context: Context, var view: View,
    private var cardData: List<GetCardData>,
    private var lastCheckedPosition: Int = -1

) :
    RecyclerView.Adapter<CardAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cardRadioButton: RadioButton = view.findViewById(R.id.cardRadioButton)
        var cardRadioGroup: RadioGroup = view.findViewById(R.id.radioButtonGroup)
        var cardNumber: TextView = view.findViewById(R.id.cardNumber)

    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout, parent, false)
        return MyViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.cardNumber.text = cardData[position].cardNumber.toString()
            .substring(cardData[position].cardNumber!!.length - 4)
        holder.cardRadioButton.isChecked = position == lastCheckedPosition
        if (holder.cardRadioButton.isChecked) {
            SharedPreference.setStringPref(
                context,
                SharedPreference.card_number,
                cardData[position].cardNumber
            )
            SharedPreference.setStringPref(
                context,
                SharedPreference.exp_month,
                cardData[position].expiryDate.toString().substring(0, 2)
            )
            SharedPreference.setStringPref(
                context,
                SharedPreference.exp_year,
                cardData[position].expiryDate.toString()
                    .substring(cardData[position].expiryDate!!.length - 4)
            )
        } else {
            SharedPreference.setStringPref(context, SharedPreference.card_number, "")
            SharedPreference.setStringPref(context, SharedPreference.exp_month, "")
            SharedPreference.setStringPref(context, SharedPreference.exp_year, "")
        }
/*
        holder.cardRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            holder.cardRadioButton.isChecked = true
        }
*/
        holder.cardRadioButton.setOnClickListener(View.OnClickListener {
            val copyOfLastCheckedPosition: Int = lastCheckedPosition
            lastCheckedPosition = position
            notifyItemChanged(copyOfLastCheckedPosition)
            notifyItemChanged(lastCheckedPosition)
        })
        /* holder.cardRadioButton.setOnCheckedChangeListener { compoundButton, b ->

             if (b){
                 index = position
              SharedPreference.setStringPref(context,SharedPreference.card_number,cardData[position].cardNumber)
              SharedPreference.setStringPref(context,SharedPreference.exp_month,cardData[position].expiryDate.toString().substring(0,2))
              SharedPreference.setStringPref(context,SharedPreference.exp_year,cardData[position].expiryDate.toString().substring(3,7))
             }else{
                 SharedPreference.setStringPref(context,SharedPreference.card_number,"")
                 SharedPreference.setStringPref(context,SharedPreference.exp_month,"")
                 SharedPreference.setStringPref(context,SharedPreference.exp_year,"")
             }
         }*/
    }


    override fun getItemCount(): Int {
        return cardData.size
    }
}