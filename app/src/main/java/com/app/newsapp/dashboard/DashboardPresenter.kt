package com.app.newsapp.dashboard

import android.content.Context
import android.util.Log
import com.app.newsapp.R
import com.app.newsapp.dashboard.model.Article
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
    private var disposable: Disposable? = null
    private var listData: List<Article>? = null


    init {
        view.setPresenter(this)
    }

    override fun start() {
    }

    override fun stop() {
        disposable?.dispose()
        view.dismissDialog()
    }

    override fun callNewsAPI(date: String, _publishedAt: String, _apiKeys: String) {
        if (!view.isNetworkAvailable()) {
            getDataFromDB()
            view.showNetworkUnavailableMsg()
            return
        }
        //show progressdilaog
        view.shoDialog()
        disposable = dashboardRepositoy.callLoginApi(date, _publishedAt, _apiKeys)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status!! == "ok") {
                    view.newsFetched(it.articles!!)
                    dashboardRepositoy.insertDataRepo(it.articles)
                    view.dismissDialog()
                } else {
                    view.showSomeErrorOccurredMsg(context.getString(R.string.error_msg))

                    view.dismissDialog()
                }

            }, {
                Log.e(TAG, it.toString())
                if (view.isActivityRunning()) {
                    view.dismissDialog()
                    if (view.isNetworkAvailable())
                        view.showSomeErrorOccurredMsg(view.getContext().getString(R.string.someErrorOccurred))
                    else view.showNetworkUnavailableMsg()
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

    /* override fun insertIntoDB(list: List<Article>) {
         Completable.fromAction {
             mdb.newsDataDao().insert(list)
         }.observeOn(AndroidSchedulers.mainThread())
             .subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver {
                 override fun onSubscribe(d: Disposable) {

                 }

                 override fun onComplete() {
                     view.dismissDialog()
                     showToast(context, "Successfully Inserted")
                 }

                 override fun onError(e: Throwable) {
                     view.dismissDialog()
                     e.printStackTrace()
                 }
             })
     }*/

    override fun callDb() {


    }

}