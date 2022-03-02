package com.quadtric.renewash.models.packageModel

import com.google.gson.annotations.SerializedName

class ServiceList {
    @SerializedName("ser_id")
    var serId: String? = null
    @SerializedName("ser_name")
    var serName: String? = null
    @SerializedName("ser_desc")
    var serDesc: String? = null
    @SerializedName("ser_price")
    var serPrice: String? = null
    @SerializedName("ser_time")
    var serTime: String? = null
    @SerializedName("ser_status")
    var serStatus: String? = null
    @SerializedName("ser_visibetofrontend")
    var serVisibetofrontend: String? = null
    @SerializedName("ser_created_date")
    var serCreatedDate: String? = null
}