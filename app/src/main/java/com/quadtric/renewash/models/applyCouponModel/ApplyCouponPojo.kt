package com.quadtric.renewash.models.applyCouponModel

import com.google.gson.annotations.SerializedName

class ApplyCouponPojo {
    @SerializedName("status")
    var status: Int? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("data")
    var data: ArrayList<ApplyCouponData> = arrayListOf()
    @SerializedName("method")
    var method: String? = null
}