package com.quadtric.renewash.viewModels

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation.findNavController
import com.google.gson.Gson
import com.quadtric.renewash.R
import com.quadtric.renewash.apiRelatedFiles.ApiClient
import com.quadtric.renewash.apiRelatedFiles.ApiInterface
import com.quadtric.renewash.commonFunctions.Common
import com.quadtric.renewash.models.applyCouponModel.ApplyCouponPojo
import com.quadtric.renewash.models.fillinformationModels.CommonPojo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FillInformationViewModel : ViewModel() {
    @SuppressLint("StaticFieldLeak")
    private lateinit var ctx: Context

    @SuppressLint("StaticFieldLeak")
    private lateinit var view: View
    private var mapData: HashMap<String, String> = HashMap()

    private val commonPojo: MutableLiveData<CommonPojo> by lazy {
        MutableLiveData<CommonPojo>().also {
            loadUserData(it)
        }
    }

    fun bookAppointment(
        map: HashMap<String, String>,
        context: Context,
        viewCtx: View
    ): LiveData<CommonPojo> {
        mapData = map
        ctx = context
        view = viewCtx
        return commonPojo
    }

    /** CALL API HERE */
    private fun loadUserData(mutableLiveData: MutableLiveData<CommonPojo>) {
        Common.showLoadingProgress(ctx as Activity)
        // Do an asynchronous operation to fetch users.
        val apiInterface: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        apiInterface.postUserInformation(
            mapData
        )!!.enqueue(object :
            Callback<CommonPojo?> {
            override fun onResponse(
                call: Call<CommonPojo?>,
                response: Response<CommonPojo?>
            ) {
                when {
                    response.code() == 200 -> {
                        Common.dismissLoadingProgress()
                        mutableLiveData.value = response.body()
                        Log.e("VT_RESPONSE", Gson().toJson(response.body()))
                        /*  val c = view.findNavController()
                          c.popBackStack() // current fragment will be pop up from the stack
                          c.navigate(R.id.homeFragment)*/
                        findNavController(view).navigate(R.id.action_paymentOptionFragment_to_homeFragment2)
                        Toast.makeText(ctx, response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                    response.code() == 401 -> {
                        Common.dismissLoadingProgress()
                        Log.e("VT_RESPONSE", Gson().toJson(response.body()))
                        Toast.makeText(ctx, response.message(), Toast.LENGTH_SHORT).show()
                        (ctx as Activity).onBackPressed()
                    }
                    response.code() == 400 -> {
                        Common.dismissLoadingProgress()
                        Log.e("RESPONSE_MESSAGE", response.message())
                        Toast.makeText(ctx, response.message(), Toast.LENGTH_SHORT).show()
                        (ctx as Activity).onBackPressed()
                        return
                    }
                    else -> {
                        Common.dismissLoadingProgress()
                        Log.e("RESPONSE_MESSAGE", response.message())
                        Toast.makeText(ctx, response.message(), Toast.LENGTH_SHORT).show()
                        (ctx as Activity).onBackPressed()
                        return
                    }
                }
            }

            override fun onFailure(call: Call<CommonPojo?>, t: Throwable) {
                Common.dismissLoadingProgress()
                Log.e("TAG", "onFailure: " + t.message)
                (ctx as Activity).onBackPressed()
            }
        })
    }

    private lateinit var couponCode: String
    private val applyCouponPojo: MutableLiveData<ApplyCouponPojo> by lazy {
        MutableLiveData<ApplyCouponPojo>().also { applyCouponApi(it) }
    }
    fun getMutableData(ctx:Context,couponCode: String?): MutableLiveData<ApplyCouponPojo?> {
        val mutableLiveData: MutableLiveData<ApplyCouponPojo?> = MutableLiveData<ApplyCouponPojo?>()
        val apiInterface: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<ApplyCouponPojo> = apiInterface.applyCouponApi(couponCode)
        call.enqueue(object : Callback<ApplyCouponPojo?> {
            override fun onResponse(
                call: Call<ApplyCouponPojo?>,
                response: Response<ApplyCouponPojo?>
            ) {
                when {
                    response.code() == 200 -> {
                        Common.dismissLoadingProgress()
                        mutableLiveData.value = response.body()
                        Log.e("VT_RESPONSE", Gson().toJson(response.body()))
                        Toast.makeText(ctx, response.body()!!.message, Toast.LENGTH_SHORT).show()

                    }
                    response.code() == 401 -> {
                        Common.dismissLoadingProgress()
                        Log.e("VT_RESPONSE", Gson().toJson(response.body()))
                        Toast.makeText(ctx, response.message(), Toast.LENGTH_SHORT).show()
                    }
                    response.code() == 400 -> {
                        Common.dismissLoadingProgress()
                        Log.e("RESPONSE_MESSAGE", response.message())
                        Toast.makeText(ctx, response.message(), Toast.LENGTH_SHORT).show()
                        return
                    }
                    else -> {
                        Common.dismissLoadingProgress()
                        Log.e("RESPONSE_MESSAGE", response.message())
                        Toast.makeText(ctx, response.message(), Toast.LENGTH_SHORT).show()
                        return
                    }
                }
            }

            override fun onFailure(call: Call<ApplyCouponPojo?>, t: Throwable) {
                val signInResponse = ApplyCouponPojo()
                Common.dismissLoadingProgress()
                Log.e("TAG", "onFailure: " + t.message)
                (ctx as Activity).onBackPressed()
                mutableLiveData.value = signInResponse
            }
        })
        return mutableLiveData
    }
    fun applyCoupon(
        coupon: String,
        context: Context,
        viewCtx: View
    ): LiveData<ApplyCouponPojo> {
        couponCode = coupon
        ctx = context
        view = viewCtx
        return applyCouponPojo
    }

     private fun applyCouponApi(mutableLiveData: MutableLiveData<ApplyCouponPojo>) {
        Common.showLoadingProgress(ctx as Activity)
        // Do an asynchronous operation to fetch users.
        val apiInterface: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        apiInterface.applyCouponApi(
            couponCode
        ).enqueue(object :
            Callback<ApplyCouponPojo?> {
            override fun onResponse(
                call: Call<ApplyCouponPojo?>,
                response: Response<ApplyCouponPojo?>
            ) {
                when {
                    response.code() == 200 -> {
                        Common.dismissLoadingProgress()
                        mutableLiveData.value = response.body()
                        Log.e("VT_RESPONSE", Gson().toJson(response.body()))
                        Toast.makeText(ctx, response.body()!!.message, Toast.LENGTH_SHORT).show()

                    }
                    response.code() == 401 -> {
                        Common.dismissLoadingProgress()
                        Log.e("VT_RESPONSE", Gson().toJson(response.body()))
                        Toast.makeText(ctx, response.message(), Toast.LENGTH_SHORT).show()
                    }
                    response.code() == 400 -> {
                        Common.dismissLoadingProgress()
                        Log.e("RESPONSE_MESSAGE", response.message())
                        Toast.makeText(ctx, response.message(), Toast.LENGTH_SHORT).show()
                        return
                    }
                    else -> {
                        Common.dismissLoadingProgress()
                        Log.e("RESPONSE_MESSAGE", response.message())
                        Toast.makeText(ctx, response.message(), Toast.LENGTH_SHORT).show()
                        return
                    }
                }
            }

            override fun onFailure(call: Call<ApplyCouponPojo?>, t: Throwable) {
                Common.dismissLoadingProgress()
                Log.e("TAG", "onFailure: " + t.message)
                (ctx as Activity).onBackPressed()
            }
        })
    }

}