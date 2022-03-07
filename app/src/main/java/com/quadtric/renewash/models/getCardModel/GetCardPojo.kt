package com.quadtric.renewash.models.getCardModel

import com.google.gson.annotations.SerializedName

class GetCardPojo {

    @SerializedName("status")
    var status: Int? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("data")
    var data: ArrayList<GetCardData> = arrayListOf()
    @SerializedName("method")
    var method: String? = null
}