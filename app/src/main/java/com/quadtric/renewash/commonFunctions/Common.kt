package com.quadtric.renewash.commonFunctions

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.Window
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.TooltipCompat
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.quadtric.renewash.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object Common {
    @SuppressLint("SetTextI18n", "InflateParams", "SimpleDateFormat")
    fun showSummaryDialog(
        ctx: Activity,
        vehicleName: String,
        packageName: String,
        addOns: String,
        date: String,
        time: String,
        total: String
    ) {
        val dialog = BottomSheetDialog(ctx, R.style.BaseBottomSheetDialog)
        val view = ctx.layoutInflater.inflate(R.layout.summary_bottomsheet, null)
        val vehicleTypeTextView: TextView = view.findViewById(R.id.vehicleTypeTextView)
        val packageTextView: TextView = view.findViewById(R.id.packageTextView)
        val addOnsTextView: TextView = view.findViewById(R.id.addOnsTextView)
        val dateTv: TextView = view.findViewById(R.id.dateTextView)
        val timeTv: TextView = view.findViewById(R.id.timeTextView)
        val totalTv: TextView = view.findViewById(R.id.totalTextView)
        vehicleTypeTextView.text = vehicleName
        packageTextView.text = packageName
        addOnsTextView.text = addOns
        dateTv.text =date
        timeTv.text = time
        if (total.isEmpty()) {
            totalTv.text = "$0"
        } else {
            totalTv.text = "$$total"
        }
        dialog.setContentView(view)
        dialog.show()
    }

    const val BaseImageUrl: String = "https://mybestbot.com/renewashN/assets/uploads/"
    private var dialog: Dialog? = null
    fun dismissLoadingProgress() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
            dialog = null
        }
    }

    fun showLoadingProgress(context: Activity) {
        if (dialog != null) {
            dialog!!.dismiss()
            dialog = null
        }
        dialog = Dialog(context)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setContentView(R.layout.dlg_progress)
        val progressBar: ProgressBar = dialog!!.findViewById(R.id.progress_bar)
        progressBar.isVisible = true
        dialog!!.setCancelable(false)
        dialog!!.show()
    }

    fun removeDuplicates(str: String): String {
        var result = ""
        for (i in str.indices) {
            if (!result.contains((str[i]))) {
                result += str[i]
            }
        }
        return result
    }

    fun isValidEmail(strPattern: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(strPattern).matches()
    }
    fun checkForInternet(context: Context): Boolean {
        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}