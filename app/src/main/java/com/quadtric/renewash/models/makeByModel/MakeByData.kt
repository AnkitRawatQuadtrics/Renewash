package com.quadtric.renewash.models.makeByModel

import com.google.gson.annotations.SerializedName

class MakeByData {
    @SerializedName("id")
    var id: String? = null
    @SerializedName("car_type")
    var carType: String? = null
    @SerializedName("year")
    var year: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("status")
    var status: String? = null
    @SerializedName("created_at")
    var createdAt: String? = null
    @SerializedName("updated_at")
    var updatedAt: String? = null
}