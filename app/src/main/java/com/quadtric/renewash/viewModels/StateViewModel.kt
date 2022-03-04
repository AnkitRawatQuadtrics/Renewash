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
import com.quadtric.renewash.models.stateModels.StatePojo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StateViewModel :ViewModel(){
    fun getStates(
        ctx: Context
    ): MutableLiveData<StatePojo?> {
        Common.showLoadingProgress(ctx as Activity)
        val mutableLiveData: MutableLiveData<StatePojo?> = MutableLiveData<StatePojo?>()
        val apiInterface: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<StatePojo> = apiInterface.getStates()
        call.enqueue(object : Callback<StatePojo?> {
            override fun onResponse(
                call: Call<StatePojo?>,
                response: Response<StatePojo?>
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

            override fun onFailure(call: Call<StatePojo?>, t: Throwable) {
                val signInResponse = StatePojo()
                Common.dismissLoadingProgress()
                Log.e("TAG", "onFailure: " + t.message)
                Toast.makeText(ctx, "onFailure: " + t.message, Toast.LENGTH_SHORT).show()
                ctx.onBackPressed()
                mutableLiveData.value = signInResponse
            }
        })
        return mutableLiveData
    }

}