package com.quadtric.renewash.models.pamentTypeModel

import com.google.gson.annotations.SerializedName

class PaymentTypeData {
    @SerializedName("ps_id")
    var psId: String? = null
    @SerializedName("ps_type")
    var psType: String? = null
    @SerializedName("ps_status")
    var psStatus: String? = null
}