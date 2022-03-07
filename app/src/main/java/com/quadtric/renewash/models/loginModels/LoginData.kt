package com.quadtric.renewash.models.loginModels

import com.google.gson.annotations.SerializedName

class LoginData {
    @SerializedName("u_id")
    var uId: String? = null
    @SerializedName("u_name")
    var uName: String? = null
    @SerializedName("u_username")
    var uUsername: String? = null
    @SerializedName("u_password")
    var uPassword: String? = null
    @SerializedName("u_phonenumber")
    var uPhonenumber: String? = null
    @SerializedName("u_address")
    var uAddress: String? = null
    @SerializedName("u_role")
    var uRole: String? = null
    @SerializedName("device_type")
    var deviceType: String? = null
    @SerializedName("device_token")
    var deviceToken: String? = null
    @SerializedName("u_rolename")
    var uRolename: String? = null
    @SerializedName("u_resetpass")
    var uResetpass: String? = null
    @SerializedName("u_created_date")
    var uCreatedDate: String? = null
}