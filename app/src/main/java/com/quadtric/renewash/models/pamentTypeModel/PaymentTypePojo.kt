package com.quadtric.renewash.models.pamentTypeModel

import com.google.gson.annotations.SerializedName

class PaymentTypePojo {
    @SerializedName("status")
    var status: Int? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("data")
    var data: ArrayList<PaymentTypeData> = arrayListOf()
    @SerializedName("method")
    var method: String? = null

}