package com.quadtric.renewash.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

import androidx.recyclerview.widget.RecyclerView
import com.quadtric.renewash.R
import com.quadtric.renewash.commonFunctions.Common
import com.quadtric.renewash.commonFunctions.SharedPreference
import com.quadtric.renewash.models.dayDateModel.TimeData


internal class TimeAdapter(
    var context: Context,
    private var index: Int = -1
) :
    RecyclerView.Adapter<TimeAdapter.MyViewHolder>() {

    var timeData = ArrayList<TimeData>()

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var timeTextView: TextView = view.findViewById(R.id.timeTextView)
        var timeCardView: CardView = view.findViewById(R.id.timeCardView)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.time_layout, parent, false)
        return MyViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CheckResult", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.timeTextView.text = timeData[position].time
        holder.timeTextView.setBackgroundResource(R.drawable.time_back_unselected)
        holder.timeTextView.setTextColor(ContextCompat.getColor(context, R.color.dark_grey))

        if (timeData[position].isClicked) {
            holder.timeTextView.setBackgroundResource(R.drawable.time_back_selected)
            holder.timeTextView.setTextColor(ContextCompat.getColor(context, R.color.blue))
            Log.e("knsdjlsandsa", timeData[position].time.toString())
            Log.e("dateTime", timeData[position].datetime.toString())
            SharedPreference.setStringPref(context, SharedPreference.time, timeData[position].time)
            SharedPreference.setStringPref(
                context,
                SharedPreference.date_time,
                timeData[position].datetime
            )
            timeData[position].isClicked = true
            index = position

        }
        holder.timeCardView.setOnClickListener {
            if (index >= 0) {
                timeData[index].isClicked = false
                notifyItemChanged(index)
            }
            index = position
            timeData[position].isClicked = true
            notifyItemChanged(position)
        }

        if (timeData[position].isBlocked == 1) {
            Log.e("dsfsdfdsf", timeData[position].time.toString())
            holder.timeTextView.setBackgroundResource(R.drawable.time_booked_back)
            holder.timeTextView.setTextColor(ContextCompat.getColor(context, R.color.red))
            holder.timeCardView.isClickable = false
        } else {
            holder.timeCardView.isClickable = true
        }
    }

    override fun getItemCount(): Int {
        return timeData.size
    }
}

