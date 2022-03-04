package com.quadtric.renewash.viewModels

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.quadtric.renewash.apiRelatedFiles.ApiClient
import com.quadtric.renewash.apiRelatedFiles.ApiInterface
import com.quadtric.renewash.commonFunctions.Common
import com.quadtric.renewash.models.addonModels.AddonPojo
import com.quadtric.renewash.models.subscriptionModels.SubscriptionPojo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddonsViewModel : ViewModel() {
    private lateinit var packageId: String
    @SuppressLint("StaticFieldLeak")
    lateinit var ctx: Context
    private val addonPojo: MutableLiveData<AddonPojo> by lazy {
        MutableLiveData<AddonPojo>().also {
            loadPackages(it)
        }
    }

    fun getAddons(id: String,context: Context): LiveData<AddonPojo> {
        packageId = id
        ctx = context
        return addonPojo
    }

    /** CALL API HERE */
    private fun loadPackages(mutableLiveData: MutableLiveData<AddonPojo>) {
        Common.showLoadingProgress(ctx as Activity)
        // Do an asynchronous operation to fetch users.
        val apiInterface: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        apiInterface.getAddOns(packageId).enqueue(object :
            Callback<AddonPojo?> {
            override fun onResponse(
                call: Call<AddonPojo?>,
                response: Response<AddonPojo?>
            ) {
                when {
                    response.code() == 200 -> {
                        Common.dismissLoadingProgress()
                        mutableLiveData.value = response.body()
                        Log.e("VT_RESPONSE", Gson().toJson(response.body()))
                    }
                    response.code() == 401 -> {
                        Common.dismissLoadingProgress()
                        Log.e("VT_RESPONSE", Gson().toJson(response.body()))
                        Toast.makeText(ctx, response.message(),Toast.LENGTH_SHORT).show()
                    }
                    response.code() == 400 -> {
                        Common.dismissLoadingProgress()
                        Log.e("RESPONSE_MESSAGE", response.message())
                        Toast.makeText(ctx, response.message(),Toast.LENGTH_SHORT).show()
                        return
                    }
                    else -> {
                        Common.dismissLoadingProgress()
                        Log.e("RESPONSE_MESSAGE", response.message())
                        Toast.makeText(ctx, response.message(),Toast.LENGTH_SHORT).show()
                        return
                    }
                }
            }

            override fun onFailure(call: Call<AddonPojo?>, t: Throwable) {
                Log.e("TAG", "onFailure: " + t.message)
                Toast.makeText(ctx, "onFailure: " + t.message, Toast.LENGTH_SHORT).show()
                Common.dismissLoadingProgress()
                (ctx as Activity).onBackPressed()
            }
        })
    }

    private val subscriptionPojo: MutableLiveData<SubscriptionPojo> by lazy {
        MutableLiveData<SubscriptionPojo>().also {
            loadSubscription(it)
        }
    }

    fun getSubscription(id: String,context: Context): LiveData<SubscriptionPojo> {
        packageId = id
        ctx = context
        return subscriptionPojo
    }

    /** CALL API HERE */
    private fun loadSubscription(mutableLiveData: MutableLiveData<SubscriptionPojo>) {
        Common.showLoadingProgress(ctx as Activity)
        // Do an asynchronous operation to fetch users.
        val apiInterface: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        apiInterface.getSubscription(packageId).enqueue(object :
            Callback<SubscriptionPojo?> {
            override fun onResponse(
                call: Call<SubscriptionPojo?>,
                response: Response<SubscriptionPojo?>
            ) {
                when {
                    response.code() == 200 -> {
                        Common.dismissLoadingProgress()
                        mutableLiveData.value = response.body()
                        Log.e("VT_RESPONSE", Gson().toJson(response.body()))
                    }
                    response.code() == 401 -> {
                        Common.dismissLoadingProgress()
                        Log.e("VT_RESPONSE", Gson().toJson(response.body()))
                        Toast.makeText(ctx, response.message(),Toast.LENGTH_SHORT).show()
                    }
                    response.code() == 400 -> {
                        Common.dismissLoadingProgress()
                        Log.e("RESPONSE_MESSAGE", response.message())
                        Toast.makeText(ctx, response.message(),Toast.LENGTH_SHORT).show()
                        return
                    }
                    else -> {
                        Common.dismissLoadingProgress()
                        Log.e("RESPONSE_MESSAGE", response.message())
                        Toast.makeText(ctx, response.message(),Toast.LENGTH_SHORT).show()
                        return
                    }
                }
            }

            override fun onFailure(call: Call<SubscriptionPojo?>, t: Throwable) {
                Log.e("TAG", "onFailure: " + t.message)
                Common.dismissLoadingProgress()
                Toast.makeText(ctx, "onFailure: " + t.message, Toast.LENGTH_SHORT).show()
                (ctx as Activity).onBackPressed()
            }
        })
    }
}