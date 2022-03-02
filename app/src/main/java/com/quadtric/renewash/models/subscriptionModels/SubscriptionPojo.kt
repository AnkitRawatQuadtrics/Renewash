package com.quadtric.renewash.models.subscriptionModels

import com.google.gson.annotations.SerializedName

class SubscriptionPojo {
    @SerializedName("status")
    var status: Int? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("data")
    var data: ArrayList<SubscriptionData> = arrayListOf()
    @SerializedName("method")
    var method: String? = null
}