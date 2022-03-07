package com.quadtric.renewash.models.loginModels

import com.google.gson.annotations.SerializedName

class LoginPojo {
    @SerializedName("status")
    var status: Int? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("data")
    var data: LoginData? = LoginData()
    @SerializedName("method")
    var method: String? = null

}