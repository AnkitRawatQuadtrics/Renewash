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
import com.quadtric.renewash.models.carModel.ModelPojo
import com.quadtric.renewash.models.vehicleTypeModel.VehicleTypePojo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VehicleTypeViewModel : ViewModel() {


    private val modelPojo= MutableLiveData<VehicleTypePojo>()

    val vehicleTypeData:LiveData<VehicleTypePojo> = modelPojo

    fun getVehicleType(ctx:Context,makeBy:String,name: String) {
        loadUsers(ctx,makeBy,name)
    }
    /** CALL API HERE */
    private fun loadUsers(ctx:Context,makeBy:String,modelName: String) {
        Common.showLoadingProgress(ctx as Activity)
        // Do an asynchronous operation to fetch users.
        val apiInterface: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        apiInterface.getVehicleType(makeBy,modelName).enqueue(object :
            Callback<VehicleTypePojo?> {
            override fun onResponse(
                call: Call<VehicleTypePojo?>,
                response: Response<VehicleTypePojo?>
            ) {
                when {
                    response.code() == 200 -> {
                        Common.dismissLoadingProgress()
                        modelPojo.value = response.body()
                    }
                    response.code() == 401 -> {
                        Common.dismissLoadingProgress()
                        Log.e("VT_RESPONSE", Gson().toJson(response.body()))
                        Toast.makeText(ctx, response.message(), Toast.LENGTH_SHORT).show()
                    }
                    response.code() == 400 -> {
                        Common.dismissLoadingProgress()
                        Log.e("RESPONSE_MESSAGE", response.message())
//                        mutableLiveData.value = response.body()
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