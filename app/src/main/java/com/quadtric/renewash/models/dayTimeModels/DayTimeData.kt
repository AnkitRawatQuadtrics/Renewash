package com.quadtric.renewash.models.dayTimeModels

import com.google.gson.annotations.SerializedName

class DayTimeData {
    @SerializedName("t_id")
    var tId: String? = null
    @SerializedName("t_day")
    var tDay: String? = null
    @SerializedName("t_status")
    var tStatus: String? = null
    @SerializedName("created_at")
    var createdAt: String? = null
    @SerializedName("updated_at")
    var updatedAt: String? = null
    @SerializedName("timeduration")
    var timeduration: ArrayList<TimeData> = arrayListOf()
}