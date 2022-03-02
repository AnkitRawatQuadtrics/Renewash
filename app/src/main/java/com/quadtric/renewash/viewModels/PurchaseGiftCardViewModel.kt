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
import com.quadtric.renewash.models.fillinformationModels.CommonPojo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PurchaseGiftCardViewModel : ViewModel() {
    @SuppressLint("StaticFieldLeak")
    private lateinit var ctx: Context
    private var mapData: HashMap<String, String> = HashMap()
    private val purchaseGiftPojo: MutableLiveData<CommonPojo> by lazy {
        MutableLiveData<CommonPojo>().also {
            loadUsers(it)
        }
    }

    fun purchaseGift(
        context: Context,
        map: HashMap<String, String>
    ): LiveData<CommonPojo> {
        ctx = context
        mapData = map
        return purchaseGiftPojo
    }

    /** CALL API HERE */
    private fun loadUsers(mutableLiveData: MutableLiveData<CommonPojo>) {
        Common.showLoadingProgress(ctx as Activity)
        // Do an asynchronous operation to fetch users.
        val apiInterface: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        apiInterface.purchaseGiftCard(
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
                        Toast.makeText(ctx, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        (ctx as Activity).onBackPressed()
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
                Log.e("TAG", "onFailure: " + t.message)
                (ctx as Activity).onBackPressed()
                Common.dismissLoadingProgress()
            }
        })
    }
}