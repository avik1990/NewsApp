package com.app.newsapp.dashboard.servicecall

import com.app.newsapp.dashboard.model.NewsResponse
import com.app.others.APIService
import io.reactivex.Observable

class DashboardRepositoy (private val apiService: APIService){

    fun callLoginApi(apiKey: String, date: String): Observable<NewsResponse> {
        /* Model class to pass data */
        // Log.d("RequestString", ObjectToJSonConvertor.getRequestJson(signUpRequest))
        //@Query("date") date: String,@Query("sortBy") sortBy: String,@Query("apiKey") apiKey: String


        return apiService.getNewsResponse("2019-08-24","publishedAt","4eb1483620174fba970d74a1cd7e300f")
    }

}