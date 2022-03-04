package com.quadtric.renewash.models.stateModels

import com.google.gson.annotations.SerializedName

class StatePojo {
    @SerializedName("status")
    var status: Int? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("data")
    var data: ArrayList<StateData> = arrayListOf()
    @SerializedName("method")
    var method: String? = null

}