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
import com.quadtric.renewash.models.dayTimeModels.DayTimePojo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DayTimeViewModel : ViewModel() {
    @SuppressLint("StaticFieldLeak")
    private lateinit var ctx: Context
    private val dayTimePojo: MutableLiveData<DayTimePojo> by lazy {
        MutableLiveData<DayTimePojo>().also {
            loadPackages(it)
        }
    }

    fun getDayDate(context: Context): LiveData<DayTimePojo> {
        ctx = context
        return dayTimePojo
    }

    /** CALL API HERE */
    private fun loadPackages(mutableLiveData: MutableLiveData<DayTimePojo>) {
        Common.showLoadingProgress(ctx as Activity)
        // Do an asynchronous operation to fetch users.
        val apiInterface: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        apiInterface.getDayTime().enqueue(object :
            Callback<DayTimePojo?> {
            override fun onResponse(
                call: Call<DayTimePojo?>,
                response: Response<DayTimePojo?>
            ) {
                when {
                    response.code() == 200 -> {
                        Common.dismissLoadingProgress()
                        mutableLiveData.value = response.body()
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

            override fun onFailure(call: Call<DayTimePojo?>, t: Throwable) {
                Log.e("TAG", "onFailure: " + t.message)
                Toast.makeText(ctx, "onFailure: " + t.message, Toast.LENGTH_SHORT).show()
                (ctx as Activity).onBackPressed()
            }
        })
    }
}

