package com.app.newsapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import android.view.Gravity
import android.widget.Toast
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun isConnectedToNetwork(context: Context?): Boolean {
    //return true
    val connectivityManager = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    return activeNetwork?.isConnectedOrConnecting == true
}

fun showSnackbar(parentLayoutId: View, msg: String, timeInMillis: Int) {
    Snackbar.make(parentLayoutId, msg, timeInMillis).show()
}

fun showToast(context: Context?,msg: String) {
    val toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
}

fun getFormattedDate(normal_date: String): String {
    Log.d("DateFormat", normal_date)
    var formated_date = ""
    if (normal_date.length > 6) {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        originalFormat.timeZone = TimeZone.getTimeZone("GMT")
        val targetFormat = SimpleDateFormat("yyyy-MM-dd")
        val date: Date
        try {
            date = originalFormat.parse(normal_date)
            formated_date = targetFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

    } else {
        formated_date = normal_date
    }
    return formated_date
}