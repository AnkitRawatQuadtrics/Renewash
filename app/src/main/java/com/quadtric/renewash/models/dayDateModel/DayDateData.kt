package com.quadtric.renewash.models.dayDateModel

import com.google.gson.annotations.SerializedName

class DayDateData {
    @SerializedName("date")
    var date: String? = null
    @SerializedName("timestamp")
    var timestamp: Int? = null
    @SerializedName("timeArr")
    var timeArr: ArrayList<TimeData> = arrayListOf()
    var isCheck = false
}