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
import com.quadtric.renewash.models.pamentTypeModel.PaymentTypePojo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentTypeViewModels : ViewModel() {
    lateinit var packageId: String

    @SuppressLint("StaticFieldLeak")
    lateinit var ctx: Context
    private val paymentTypePojo: MutableLiveData<PaymentTypePojo> by lazy {
        MutableLiveData<PaymentTypePojo>().also {
            loadPackages(it)
        }
    }

    fun getPaymentType(context: Context): LiveData<PaymentTypePojo> {
        ctx = context
        return paymentTypePojo
    }

    /** CALL API HERE */
    private fun loadPackages(mutableLiveData: MutableLiveData<PaymentTypePojo>) {
        Common.showLoadingProgress(ctx as Activity)
        // Do an asynchronous operation to fetch users.
        val apiInterface: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        apiInterface.getPaymentTypes().enqueue(object :
            Callback<PaymentTypePojo?> {
            override fun onResponse(
                call: Call<PaymentTypePojo?>,
                response: Response<PaymentTypePojo?>
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

            override fun onFailure(call: Call<PaymentTypePojo?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}