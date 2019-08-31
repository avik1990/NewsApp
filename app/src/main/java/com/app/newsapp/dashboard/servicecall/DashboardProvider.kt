package com.app.newsapp.dashboard.servicecall

import android.content.Context
import com.app.others.APIService
import com.app.others.RetrofitClient

object DashboardProvider {

    fun getNewsRepository(context: Context): DashboardRepositoy {
        return DashboardRepositoy(RetrofitClient.getClient()!!.create(APIService::class.java),context)
    }
}