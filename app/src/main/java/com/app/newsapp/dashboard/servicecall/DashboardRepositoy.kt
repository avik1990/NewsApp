package com.app.newsapp.dashboard.servicecall

import com.app.newsapp.dashboard.model.NewsResponse
import com.app.others.APIService
import com.app.others.Constants
import io.reactivex.Observable

open class DashboardRepositoy(private val apiService: APIService) {

    fun callLoginApi(date: String, _publishedAt: String, _apiKeys: String): Observable<NewsResponse> {
        return apiService.getNewsResponse(date, _publishedAt, _apiKeys)
    }

}