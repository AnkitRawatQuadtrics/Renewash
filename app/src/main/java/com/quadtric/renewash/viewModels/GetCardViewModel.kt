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
import com.quadtric.renewash.models.getCardModel.GetCardPojo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetCardViewModel : ViewModel() {

     private val modelPojo= MutableLiveData<GetCardPojo>()

    val getCard:LiveData<GetCardPojo> = modelPojo

    fun getCardApi(context: Context,user_id: String) {
        loadCard(context,user_id)
    }

    /** CALL API HERE */
    private fun loadCard(ctx: Context,user_id:String) {
        Common.showLoadingProgress(ctx as Activity)
        // Do an asynchronous operation to fetch users.
        val apiInterface: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        apiInterface.getCardData(user_id).enqueue(object :
            Callback<GetCardPojo?> {
            override fun onResponse(
                call: Call<GetCardPojo?>,
                response: Response<GetCardPojo?>
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

            override fun onFailure(call: Call<GetCardPojo?>, t: Throwable) {
                Log.e("TAG", "onFailure: " + t.message)
                Common.dismissLoadingProgress()
                Toast.makeText(ctx, "onFailure: " + t.message, Toast.LENGTH_SHORT).show()
//                (ctx as Activity).onBackPressed()
            }
        })
    }

}