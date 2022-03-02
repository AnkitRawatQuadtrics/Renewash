package com.quadtric.renewash.models.carModel

import com.google.gson.annotations.SerializedName

class ModelPojo {
    @SerializedName("status")
    var status: Int? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("date")
    var data: ArrayList<ModelData> = arrayListOf()
    @SerializedName("method")
    var method: String? = null
}