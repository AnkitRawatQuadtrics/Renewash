package com.quadtric.renewash.viewModels

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
import com.quadtric.renewash.models.vehicleTypeModel.VehicleTypePojo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VehicleTypeViewModel : ViewModel() {
    private lateinit var ctx: Context
    private lateinit var makeBy: String
    private val vehicleTypePojo: MutableLiveData<VehicleTypePojo> by lazy {
        MutableLiveData<VehicleTypePojo>().also {
            loadUsers(it)
        }
    }

    fun getUsers(context: Context,make:String): LiveData<VehicleTypePojo> {
        ctx = context
        makeBy = make
        return vehicleTypePojo
    }

    /** CALL API HERE */
    private fun loadUsers(mutableLiveData: MutableLiveData<VehicleTypePojo>) {
        Common.showLoadingProgress(ctx as Activity)
        // Do an asynchronous operation to fetch users.
        val apiInterface: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        apiInterface.getVehicleType(makeBy).enqueue(object :
            Callback<VehicleTypePojo?> {
            override fun onResponse(
                call: Call<VehicleTypePojo?>,
                response: Response<VehicleTypePojo?>
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

            override fun onFailure(call: Call<VehicleTypePojo?>, t: Throwable) {
                Log.e("TAG", "onFailure: " + t.message)
                Common.dismissLoadingProgress()
                Toast.makeText(ctx, "onFailure: " + t.message, Toast.LENGTH_SHORT).show()
                (ctx as Activity).onBackPressed()
            }
        })
    }
}