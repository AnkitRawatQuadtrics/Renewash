package com.quadtric.renewash.models.dayDateModel

import com.google.gson.annotations.SerializedName

class TimeData {
    @SerializedName("datetime")
    var datetime: String? = null
    @SerializedName("timestamp")
    var timestamp: Int? = null
    @SerializedName("time")
    var time: String? = null
    @SerializedName("is_blocked")
    var isBlocked: Int? = null
    var isClicked = false
}