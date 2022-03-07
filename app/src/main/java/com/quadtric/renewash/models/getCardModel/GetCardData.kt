package com.quadtric.renewash.models.getCardModel

import com.google.gson.annotations.SerializedName

class GetCardData {
    @SerializedName("id")
    var id: String? = null
    @SerializedName("user_id")
    var userId: String? = null
    @SerializedName("card_number")
    var cardNumber: String? = null
    @SerializedName("expiry_date")
    var expiryDate: String? = null
    @SerializedName("created_at")
    var createdAt: String? = null
    @SerializedName("updated_at")
    var updatedAt: String? = null
}