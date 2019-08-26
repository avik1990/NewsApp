package com.app.newsapp.dashboard

import android.support.v7.app.AppCompatActivity
import com.app.newsapp.dashboard.model.Article
import com.app.others.BasePresenter
import com.app.others.BaseView

interface DashboardContract {

    interface View : BaseView<Presenter> {
        fun getContext(): AppCompatActivity ///to get activity context from presenter
        fun handleProgressAlert(showingStatus: Boolean) // true --> show, false --> dismiss
        fun newsFetched(list: List<Article>)
        fun newsFetchedDB(list: List<Article>)
    }

    interface Presenter : BasePresenter {
        fun callNewsAPI(apikey: String)
        fun getDataFromDB()
        fun insertIntoDB(list: List<Article>)
    }

}