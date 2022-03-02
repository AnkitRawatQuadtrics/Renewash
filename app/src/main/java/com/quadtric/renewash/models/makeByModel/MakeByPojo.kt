package com.quadtric.renewash.models.makeByModel

import com.google.gson.annotations.SerializedName

class MakeByPojo {
    @SerializedName("status"  ) var status  : Int?            = null
    @SerializedName("message" ) var message : String?         = null
    @SerializedName("data"    ) var data    : ArrayList<MakeByData> = arrayListOf()
    @SerializedName("method"  ) var method  : String?         = null
}