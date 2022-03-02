package com.quadtric.renewash.models.dayTimeModels

import com.google.gson.annotations.SerializedName

class DayTimePojo {
    @SerializedName("status")
    var status: Int? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("data")
    var data: ArrayList<DayTimeData> = arrayListOf()
    @SerializedName("method")
    var method: String? = null
}