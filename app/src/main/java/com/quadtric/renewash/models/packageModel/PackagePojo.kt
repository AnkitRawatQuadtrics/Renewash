package com.quadtric.renewash.models.packageModel

import com.google.gson.annotations.SerializedName

class PackagePojo {
    @SerializedName("status")
    var status: Int? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("data")
    var data: ArrayList<PackageData> = arrayListOf()
    @SerializedName("method")
    var method: String? = null
}