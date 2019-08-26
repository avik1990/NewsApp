package com.app.newsapp.details

import android.support.v7.app.AppCompatActivity

interface DetailsContract {

    interface View {
        fun getContext(): AppCompatActivity ///to get activity context from presenter
        fun handleProgressAlert(showingStatus: Boolean) // true --> show, false --> dismiss
        fun newsDetailsFetched()
    }


}