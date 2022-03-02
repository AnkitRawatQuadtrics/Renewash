package com.quadtric.renewash.models.dayDateModel

import com.google.gson.annotations.SerializedName

class DayDatePojo {
    @SerializedName("status")
    var status: Int? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("date")
    var data: ArrayList<DayDateData> = arrayListOf()
    @SerializedName("method")
    var method: String? = null
}