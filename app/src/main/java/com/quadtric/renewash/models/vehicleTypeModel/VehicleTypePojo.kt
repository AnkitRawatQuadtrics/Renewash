package com.quadtric.renewash.models.vehicleTypeModel

import com.google.gson.annotations.SerializedName


data class VehicleTypePojo(
    @SerializedName("status") var status: Int? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("data") var data: ArrayList<VehicleTypeData> = arrayListOf(),
    @SerializedName("method") var method: String? = null
)
