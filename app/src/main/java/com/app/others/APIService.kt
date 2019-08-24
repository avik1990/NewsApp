package com.app.others

import com.app.newsapp.dashboard.model.NewsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("v2/everything?q=bitcoin")
    fun getNewsResponse(@Query("date") date: String,@Query("sortBy") sortBy: String,@Query("apiKey") apiKey: String): Observable<NewsResponse>
}