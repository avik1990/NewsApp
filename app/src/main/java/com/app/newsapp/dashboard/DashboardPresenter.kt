package com.app.newsapp.dashboard

import android.content.Context
import com.app.newsapp.dashboard.model.Article
import com.app.newsapp.dashboard.model.NewsResponse
import com.app.newsapp.dashboard.servicecall.DashboardRepositoy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.CompletableObserver
import io.reactivex.Completable

open class DashboardPresenter(
    private val context: Context,
    private val view: DashboardContract.View,
    private val dashboardRepositoy: DashboardRepositoy
) :
    DashboardContract.Presenter {

    val TAG = "DashboardPresenter"
    private var listData: List<Article>? = null


    init {
        view.setPresenter(this)
    }

    override fun start() {
    }

    override fun stop() {
        view.dismissDialog()
    }

    override fun callNewsAPI(date: String, _publishedAt: String, _apiKeys: String) {
        if (!view.isNetworkAvailable()) {
            getDataFromDB()
            view.showNetworkUnavailableMsg()
            return
        }
        view.shoDialog()
        dashboardRepositoy.callNewsApi(date, _publishedAt, _apiKeys,
            object : DataSourceCallBack<NewsResponse> {
                override fun onSuccess(responseData: NewsResponse) {
                    view.newsFetched(responseData.articles!!)
                    dashboardRepositoy.insertDataRepo(responseData.articles)
                    view.dismissDialog()
                }

                override fun onError(errorMsg: String) {
                    view.showSomeErrorOccurredMsg(errorMsg)

                    view.dismissDialog()
                }

            })

    }

    override fun getDataFromDB() {
        view.shoDialog()
        Completable.fromAction {
            listData = dashboardRepositoy.getDataRepo()
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onComplete() {
                    view.dismissDialog()
                    if (listData!!.isNotEmpty()) {
                        view.newsFetchedDB(listData!!)
                    }
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
    }

}