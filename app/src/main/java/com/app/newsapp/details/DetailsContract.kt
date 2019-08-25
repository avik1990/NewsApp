package com.app.newsapp.details

import android.support.v7.app.AppCompatActivity
import com.app.newsapp.dashboard.model.NewsResponse
import com.app.others.BasePresenter
import com.app.others.BaseView

interface DetailsContract {

    interface View {
        fun getContext(): AppCompatActivity ///to get activity context from presenter
        fun handleProgressAlert(showingStatus: Boolean) // true --> show, false --> dismiss
        fun newsDetailsFetched()
    }


}