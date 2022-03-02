package com.quadtric.renewash.models.dayTimeModels

import com.google.gson.annotations.SerializedName

class TimeData {
    @SerializedName("id")
    var id: String? = null
    @SerializedName("timing_id")
    var timingId: String? = null
    @SerializedName("time")
    var time: String? = null
    @SerializedName("created_at")
    var createdAt: String? = null
    @SerializedName("updated_at")
    var updatedAt: String? = null
}