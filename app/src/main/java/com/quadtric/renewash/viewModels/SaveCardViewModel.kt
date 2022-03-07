package com.quadtric.renewash.viewModels

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.quadtric.renewash.R
import com.quadtric.renewash.apiRelatedFiles.ApiClient
import com.quadtric.renewash.apiRelatedFiles.ApiInterface
import com.quadtric.renewash.commonFunctions.Common
import com.quadtric.renewash.models.fillinformationModels.CommonPojo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SaveCardViewModel:ViewModel() {
    fun saveCardApi(mapData: HashMap<String, String>, ctx: Context): MutableLiveData<CommonPojo?> {
        Common.showLoadingProgress(ctx as Activity)
        val mutableLiveData: MutableLiveData<CommonPojo?> = MutableLiveData<CommonPojo?>()
        val apiInterface: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<CommonPojo>? = apiInterface.saveCard(mapData)
        call!!.enqueue(object : Callback<CommonPojo?> {
            override fun onResponse(
                call: Call<CommonPojo?>,
                response: Response<CommonPojo?>
            ) {
                when {
                    response.code() == 200 -> {
                        Common.dismissLoadingProgress()
                        mutableLiveData.value = response.body()
                        Log.e("VT_RESPONSE", Gson().toJson(response.body()))
                        Toast.makeText(ctx, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        Common.dismissLoadingProgress()
                    }
                    response.code() == 401 -> {
                        Common.dismissLoadingProgress()
                        Log.e("VT_RESPONSE", Gson().toJson(response.body()))
                        Toast.makeText(ctx, response.message(), Toast.LENGTH_SHORT).show()
                        Common.dismissLoadingProgress()
                    }
                    response.code() == 400 -> {
                        Common.dismissLoadingProgress()
                        Log.e("RESPONSE_MESSAGE", response.message())
                        Toast.makeText(ctx, response.message(), Toast.LENGTH_SHORT).show()
                        Common.dismissLoadingProgress()
                        return
                    }
                    else -> {
                        Common.dismissLoadingProgress()
                        Log.e("RESPONSE_MESSAGE", response.message())
                        Toast.makeText(ctx, response.message(), Toast.LENGTH_SHORT).show()
                        Common.dismissLoadingProgress()
                        return
                    }
                }
            }

            override fun onFailure(call: Call<CommonPojo?>, t: Throwable) {
                val signInResponse = CommonPojo()
                Common.dismissLoadingProgress()
                Log.e("TAG", "onFailure: " + t.message)
                Toast.makeText(ctx, "onFailure: " + t.message, Toast.LENGTH_SHORT).show()
                (ctx as Activity).onBackPressed()
                mutableLiveData.value = signInResponse
            }
        })
        return mutableLiveData
    }

}