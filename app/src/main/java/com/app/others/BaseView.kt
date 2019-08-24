package com.app.others

interface BaseView<T> {
    fun goToNextPage()
    fun isActivityRunning(): Boolean
    fun setPresenter(presenter: T)
    fun isNetworkAvailable():Boolean
    fun showNetworkUnavailableMsg()
    fun showSomeErrorOccurredMsg(msg:String)
}