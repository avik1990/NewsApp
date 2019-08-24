package com.app.newsapp.dashboard.servicecall

import com.app.others.APIService
import com.app.others.RetrofitClient

object DashboardProvider {

    fun getNewsRepository(): DashboardRepositoy {
        return DashboardRepositoy(RetrofitClient.getClient()!!.create(APIService::class.java))
    }
}