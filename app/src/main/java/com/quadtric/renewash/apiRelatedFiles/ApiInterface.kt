@file:Suppress("SpellCheckingInspection")

package com.quadtric.renewash.apiRelatedFiles

import com.quadtric.renewash.models.CommonResponse
import com.quadtric.renewash.models.addonModels.AddonPojo
import com.quadtric.renewash.models.applyCouponModel.ApplyCouponPojo
import com.quadtric.renewash.models.carModel.ModelPojo
import com.quadtric.renewash.models.dayDateModel.DayDatePojo
import com.quadtric.renewash.models.dayTimeModels.DayTimePojo
import com.quadtric.renewash.models.fillinformationModels.CommonPojo
import com.quadtric.renewash.models.makeByModel.MakeByPojo
import com.quadtric.renewash.models.packageModel.PackagePojo
import com.quadtric.renewash.models.pamentTypeModel.PaymentTypePojo
import com.quadtric.renewash.models.stateModels.StatePojo
import com.quadtric.renewash.models.subscriptionModels.SubscriptionPojo
import com.quadtric.renewash.models.vehicleTypeModel.VehicleTypePojo
import retrofit2.Call
import retrofit2.http.*

interface
ApiInterface {


    @GET("/renewashN/api/makeby")
    fun getMakeBy(): Call<MakeByPojo>

    @GET("/renewashN/api/carmodel")
    fun getModel(
        @Query("makeby_name") name: String?
    ): Call<ModelPojo>

    @GET("/renewashN/api/packages")
    fun getPackages(
        @Query("vehicle_id") packageId: String?
    ): Call<PackagePojo>

    @GET("/renewashN/api/addons")
    fun getAddOns(
        @Query("package_id") packageId: String?
    ): Call<AddonPojo>

    @GET("/renewashN/api/vechicletype")
    fun getVehicleType(
        @Query("makeby_name") makeby_name: String?
    ): Call<VehicleTypePojo>

    @GET("/renewashN/api/subscriptions")
    fun getSubscription(
        @Query("package_id") packageId: String?
    ): Call<SubscriptionPojo>

    @GET("/renewashN/api/coupon")
    fun applyCouponApi(
        @Query("gift_no") gift_no: String?
    ): Call<ApplyCouponPojo>

    @GET("/renewashN/api/timing?timezone=Asia/Kolkata")
    fun getDateTime(): Call<DayDatePojo>

    @GET("/renewashN/api/states")
    fun getStates(): Call<StatePojo>

    @GET("/renewashN/api/timeingdatamultiple")
    fun getDayTime(): Call<DayTimePojo>

    @GET("/renewashN/api/paymentmethod")
    fun getPaymentTypes(): Call<PaymentTypePojo>

    @POST("/renewashN/api/add_booking")
    @FormUrlEncoded
    fun postUserInformation(
        @FieldMap map: Map<String, Any>
    ): Call<CommonPojo>?

    @POST("/renewashN/api/giftcard")
    @FormUrlEncoded
    fun purchaseGiftCard(
        @FieldMap map: Map<String, String>
    ): Call<CommonPojo>?

    @POST("/renewashN/api/login_process")
    @FormUrlEncoded
    fun login(
        @FieldMap map: Map<String, String>
        /*  @Field("u_username") username: String?,
          @Field("u_password") password: String?*/
    ): Call<CommonPojo>?

    @POST("/renewashN/api/signup_user")
    @FormUrlEncoded
    fun signUp(
        @FieldMap map: Map<String, String>
    ): Call<CommonPojo>?

    @POST("/renewashN/api/forgotpassword")
    @FormUrlEncoded
    fun forgotPassword(
        @FieldMap map: Map<String, String>
    ): Call<CommonPojo>?

}

