package com.quadtric.renewash.models.vehicleTypeModel

import com.google.gson.annotations.SerializedName

class VehicleTypeData {
    @SerializedName("vt_id")
    var vtId: String? = null
    @SerializedName("vt_name")
    var vtName: String? = null
    @SerializedName("vt_image")
    var vtImage: String? = null
    @SerializedName("vt_status")
    var vtStatus: String? = null
    @SerializedName("vt_created_date")
    var vtCreatedDate: String? = null
}