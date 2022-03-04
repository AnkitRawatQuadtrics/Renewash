package com.quadtric.renewash.models.stateModels

import com.google.gson.annotations.SerializedName

class StateData {
    @SerializedName("id")
    var id: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("abbreviation")
    var abbreviation: String? = null
}