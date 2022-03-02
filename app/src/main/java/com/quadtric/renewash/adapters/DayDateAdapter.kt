package com.quadtric.renewash.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quadtric.renewash.R
import com.quadtric.renewash.commonFunctions.Common
import com.quadtric.renewash.commonFunctions.SharedPreference
import com.quadtric.renewash.databinding.FragmentCarWashDateTimeBinding
import com.quadtric.renewash.fragments.CarWashDateTimeFragment
import com.quadtric.renewash.models.dayDateModel.DayDateData
import com.quadtric.renewash.models.dayDateModel.TimeData
import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal class DayDateAdapter(
    var context: Context, var dayDateData: List<DayDateData>, var dateClick: DateClick,
    private var index: Int = -1
) :

    RecyclerView.Adapter<DayDateAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var dayTextView: TextView = view.findViewById(R.id.dayTextView)
        var dateTextView: TextView = view.findViewById(R.id.dateTextView)
        var mainRelativeLayout: LinearLayout = view.findViewById(R.id.mainLinearLayout)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.date_layout, parent, false)
        return MyViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CheckResult", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.dateTextView.text = dayDateData[position].date!!.substring(3, 5)
        val secondApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val timestamp = dayDateData[position].timestamp // timestamp in Long
        val timestampAsDateString = DateTimeFormatter.ISO_INSTANT
            .format(java.time.Instant.ofEpochSecond(timestamp!!.toLong()))
        val date = LocalDate.parse(timestampAsDateString, secondApiFormat)

        holder.dayTextView.text = date.dayOfWeek.toString().substring(0, 3)
        holder.mainRelativeLayout.setBackgroundResource(R.drawable.unselected_date)
        holder.dateTextView.setTextColor(ContextCompat.getColor(context, R.color.dark_grey))
        holder.dayTextView.setTextColor(ContextCompat.getColor(context, R.color.dark_grey))

        if (position == 0 && SharedPreference.getStringPref(context, "date")!!.isEmpty()) {
            holder.mainRelativeLayout.post {
                holder.mainRelativeLayout.performClick()
            }
        }
        if (dayDateData[position].isCheck) {
            Log.e("DAY", date.dayOfWeek.toString()) // prints Wednesday
            Log.e("MONTH", date.month.toString()) // prints August*/
            holder.mainRelativeLayout.setBackgroundResource(R.drawable.selected_date)
            holder.dateTextView.setTextColor(ContextCompat.getColor(context, R.color.white))
            holder.dayTextView.setTextColor(ContextCompat.getColor(context, R.color.white))
            SharedPreference.setStringPref(
                context,
                SharedPreference.date,
                dayDateData[position].date
            )

            index = position
            dateClick.dateClick(dayDateData[position].timeArr)
        }

        holder.mainRelativeLayout.setOnClickListener {
            if (index >= 0) {
                dayDateData[index].isCheck = false
                notifyItemChanged(index)
            }
            SharedPreference.setStringPref(
                context,
                SharedPreference.time,
                ""
            )
            index = position
            dayDateData[position].isCheck = true
            notifyItemChanged(position)
//            dateClick.dateClick(dayDateData[position].timeArr)
        }
    }

    interface DateClick {
        fun dateClick(dayDateData: ArrayList<TimeData>)
    }

    override fun getItemCount(): Int {
        return dayDateData.size
    }
}