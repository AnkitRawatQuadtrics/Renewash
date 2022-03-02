package com.quadtric.renewash.models.applyCouponModel

import com.google.gson.annotations.SerializedName

class ApplyCouponData {
    @SerializedName("id")
    var id: String? = null
    @SerializedName("purchase_amount")
    var purchase_amount: String? = null
    @SerializedName("user_email")
    var user_email: String? = null
    @SerializedName("gift_no")
    var gift_no: String? = null
    @SerializedName("expiry_date")
    var expiry_date: String? = null
    @SerializedName("created_at")
    var created_at: String? = null
    @SerializedName("updated_at")
    var updated_at: String? = null
}