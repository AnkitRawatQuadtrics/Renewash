package com.quadtric.renewash.models

import com.google.gson.annotations.SerializedName

class PlaceDataModel(placeId: String, toString: String) {
    @SerializedName("id")
    var id: String? = null
    @SerializedName("address")
    var address: String? = null
    @SerializedName("city")
    var city: String? = null
    @SerializedName("state")
    var state: String? = null
}