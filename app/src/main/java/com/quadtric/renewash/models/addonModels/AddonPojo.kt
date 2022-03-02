package com.quadtric.renewash.models.addonModels

import com.google.gson.annotations.SerializedName

class AddonPojo {
    @SerializedName("status")
    var status: Int? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("data")
    var data: ArrayList<AddonData> = arrayListOf()
    @SerializedName("method")
    var method: String? = null
}