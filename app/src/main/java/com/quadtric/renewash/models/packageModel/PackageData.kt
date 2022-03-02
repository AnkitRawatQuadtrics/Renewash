package com.quadtric.renewash.models.packageModel

import com.google.gson.annotations.SerializedName

class PackageData {
    @SerializedName("p_id")
    var pId: String? = null
    @SerializedName("p_name")
    var pName: String? = null
    @SerializedName("p_price")
    var pPrice: String? = null
    @SerializedName("p_time")
    var pTime: String? = null
    @SerializedName("p_vt_id")
    var pVtId: String? = null
    @SerializedName("p_s_list")
    var pSList: String? = null
    @SerializedName("p_status")
    var pStatus: String? = null
    @SerializedName("p_created_date")
    var pCreatedDate: String? = null
    @SerializedName("service_list")
    var serviceList: ArrayList<ServiceList> = arrayListOf()
}