package com.quadtric.renewash.commonFunctions

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class SharedPreference {
    companion object {
        var vehicle_type: String = "vehicle_type"
        var cat_id: String = "cat_id"
        var vehicle_package: String = "vehicle_package"
        var date: String = "date"
        var time: String = "time"
        var date_time: String = "date_time"
        var total: String = "total"
        var coupon_price: String = "coupon_price"
        var final_mount: String = "final_mount"
        var gift_no: String = "gift_no"
        var gift_amount: String = "gift_amount"
        var make: String = "make"
        var model: String = "model"
        var plate_number: String = "plate_number"
        var qt_type: String = "qt_type"
        var service_id: String = "service_id"
        var package_id: String = "package_id"
        var service_name: String = "service_name"
        var subscription_name: String = "subscription_name"
        var subscription_price: String = "subscription_price"
        const val PREF_NAME: String = "CarWashApp"
        const val PRIVATE_MODE: Int = 0

        @SuppressLint("WrongConstant")
        fun getStringPref(context: Context, key: String): String? {
            val pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
            return pref.getString(key, "")
        }

        @SuppressLint("WrongConstant")
        fun setStringPref(context: Context, key: String, value: String?) {
            val pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
            pref.edit().putString(key, value).apply()
        }

        fun setDataFromSharedPreferences(context: Context, list: List<String>) {
            val pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
            val prefsEditor: SharedPreferences.Editor = pref.edit()
            val gson = Gson()
            val json = gson.toJson(list)
            prefsEditor.putString("MyObject", json)
            prefsEditor.commit()
        }

        /*
                fun getDataList(context: Context) {
                    val pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
                    val gson = Gson()
                    val json: String = pref.getString("MyObject", "")
                    val obj: MyObject = gson.fromJson(json, MyObject::class.java)
                }
        */

/*
        @SuppressLint("WrongConstant")
        fun saveList(context: Context, list: List<String>): String? {
            val pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)

            val set: MutableSet<String> = HashSet()
            set.addAll(list)
            val editor = context.pref.edit()
            pref.putStringSet("key", set)
            scoreEditor.commit()
            pref.edit().putString(key, value).apply()
        }
*/
/*
        @SuppressLint("WrongConstant")
        fun saveData(context: Context, key: String, list: List<String>) {
            // method for saving the data in array list.
            // creating a variable for storing data in
            // shared preferences.
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
            // creating a variable for editor to
            // store data in shared preferences.
            val editor = sharedPreferences.edit()
            // creating a new variable for gson.
            val gson = Gson()
            // getting data from gson and storing it in a string.
            val json = gson.toJson(list)
            // below line is to save data in shared
            // prefs in the form of string.
            editor.putString("courses", json)
            editor.p

            // below line is to apply changes
            // and save data in shared prefs.
            editor.apply()

            // after saving data we are displaying a toast message.
            Toast.makeText(context, "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT)
                .show()
        }
*/

/*
        private fun loadData() {
            // method to load arraylist from shared prefs
            // initializing our shared prefs with name as
            // shared preferences.
            val sharedPreferences: SharedPreferences =
                getSharedPreferences("shared preferences", MODE_PRIVATE)

            // creating a variable for gson.
            val gson = Gson()

            // below line is to get to string present from our
            // shared prefs if not present setting it as null.
            val json = sharedPreferences.getString("courses", null)

            // below line is to get the type of our array list.
            val type: Type = object : TypeToken<ArrayList<CourseModal?>?>() {}.type

            // in below line we are getting data from gson
            // and saving it to our array list
            courseModalArrayList = gson.fromJson<Any>(json, type)

            // checking below if the array list is empty or not
            if (courseModalArrayList == null) {
                // if the array list is empty
                // creating a new array list.
                courseModalArrayList = ArrayList()
            }
        }
*/
    }
}