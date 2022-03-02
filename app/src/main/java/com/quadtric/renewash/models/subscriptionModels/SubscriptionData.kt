package com.quadtric.renewash.models.subscriptionModels

import com.google.gson.annotations.SerializedName

class SubscriptionData {
    @SerializedName("id")
    var id: String? = null
    @SerializedName("subscription_id")
    var subscriptionId: String? = null
    @SerializedName("price")
    var price: String? = null
    @SerializedName("package_id")
    var packageId: String? = null
    @SerializedName("created_at")
    var createdAt: String? = null
    @SerializedName("updated_at")
    var updatedAt: String? = null
    @SerializedName("subscription_name")
    var subscriptionName: String? = null

}