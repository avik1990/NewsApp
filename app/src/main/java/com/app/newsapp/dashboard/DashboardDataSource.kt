package com.app.newsapp.dashboard

interface DataSourceCallBack<T> {
    fun onSuccess(responseData: T)
    fun onError(errorMsg : String)
}