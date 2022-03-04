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
import com.quadtric.renewash.models.makeByModel.MakeByPojo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VehicleInformationViewModel : ViewModel() {
    private lateinit var ctx: Context
    private val makeByPojo: MutableLiveData<MakeByPojo> by lazy {
        MutableLiveData<MakeByPojo>().also {
            loadMakeBy(it)
        }
    }

    fun getMakeBy(context:Context): LiveData<MakeByPojo> {
        ctx = context
        return makeByPojo
    }

    /** CALL API HERE */
    private fun loadMakeBy(mutableLiveData: MutableLiveData<MakeByPojo>) {
        Common.showLoadingProgress(ctx as Activity)
        // Do an asynchronous operation to fetch users.
        val apiInterface: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        apiInterface.getMakeBy().enqueue(object :
            Callback<MakeByPojo?> {
            override fun onResponse(
                call: Call<MakeByPojo?>,
                response: Response<MakeByPojo?>
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

            override fun onFailure(call: Call<MakeByPojo?>, t: Throwable) {
                Log.e("TAG", "onFailure: " + t.message)
                Common.dismissLoadingProgress()
                Toast.makeText(ctx, "onFailure: " + t.message, Toast.LENGTH_SHORT).show()
                (ctx as Activity).onBackPressed()
            }
        })
    }


    private lateinit var name: String
    private val modelPojo: MutableLiveData<ModelPojo> by lazy {
        MutableLiveData<ModelPojo>().also {
            loadModel(it, name)
        }
    }

    fun getModel(model: String): LiveData<ModelPojo> {
        name = model
        return modelPojo
    }

    /** CALL API HERE */
    private fun loadModel(mutableLiveData: MutableLiveData<ModelPojo>, name: String) {
        Common.showLoadingProgress(ctx as Activity)
        // Do an asynchronous operation to fetch users.
        val apiInterface: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        apiInterface.getModel(name).enqueue(object :
            Callback<ModelPojo?> {
            override fun onResponse(
                call: Call<ModelPojo?>,
                response: Response<ModelPojo?>
            ) {
                when {
                    response.code() == 200 -> {
                        Common.dismissLoadingProgress()
                        mutableLiveData.value = response.body()
                        Log.e("VT_RESPONSE", Gson().toJson(response.body()))
                    }
                    response.code() == 401 -> {
                        Common.dismissLoadingProgress()
                        Toast.makeText(ctx, response.message(),Toast.LENGTH_SHORT).show()
                        Log.e("VT_RESPONSE", Gson().toJson(response.body()))
                    }
                    response.code() == 400 -> {
                        Common.dismissLoadingProgress()
                        Toast.makeText(ctx, response.message(),Toast.LENGTH_SHORT).show()
                        Log.e("RESPONSE_MESSAGE", response.message())
                        return
                    }
                    else -> {
                        Common.dismissLoadingProgress()
                        Toast.makeText(ctx, response.message(),Toast.LENGTH_SHORT).show()
                        Log.e("RESPONSE_MESSAGE", response.message())
                        return
                    }
                }
            }

            override fun onFailure(call: Call<ModelPojo?>, t: Throwable) {
                Log.e("TAG", "onFailure: " + t.message)
                Common.dismissLoadingProgress()
                Toast.makeText(ctx, "onFailure: " + t.message, Toast.LENGTH_SHORT).show()
                (ctx as Activity).onBackPressed()

            }
        })
    }

}