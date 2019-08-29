package com.app.newsapp.dashboard

import android.content.Context
import android.util.Log
import com.app.newsapp.R
import com.app.newsapp.dashboard.model.Article
import com.app.newsapp.dashboard.servicecall.DashboardRepositoy
import com.app.newsapp.db.AppDatabse
import com.app.newsapp.utils.showToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.CompletableObserver
import io.reactivex.Completable


class DashboardPresenter(
    private val context: Context,
    private val view: DashboardContract.View,
    private val dashboardRepositoy: DashboardRepositoy,
    private val mdb: AppDatabse
) :
    DashboardContract.Presenter {


    val TAG = "DashboardPresenter"
    private var disposable: Disposable? = null
    private var listData: List<Article>? = null
    private var listNewsData: List<Article>? = null

    val newsdata: Article? = null

    init {
        view.setPresenter(this)
    }

    override fun start() {
    }

    override fun stop() {
        disposable?.dispose()
        view.handleProgressAlert(false)
    }

    override fun callNewsAPI(apiKey: String) {
        if (!view.isNetworkAvailable()) {
            getDataFromDB()
            view.showNetworkUnavailableMsg()
            return
        }
        //show progressdilaog
        view.handleProgressAlert(true)
        disposable = dashboardRepositoy.callLoginApi("", "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status!! == "ok") {
                    view.newsFetched(it.articles!!)
                    insertIntoDB(it.articles)
                } else {
                    view.handleProgressAlert(false)
                }

            }, {
                Log.e(TAG, it.toString())
                if (view.isActivityRunning()) {
                    view.handleProgressAlert(false)
                    if (view.isNetworkAvailable())
                        view.showSomeErrorOccurredMsg(view.getContext().getString(R.string.someErrorOccurred))
                    else view.showNetworkUnavailableMsg()
                }
            })
    }

    override fun getDataFromDB() {
        view.handleProgressAlert(true)
        Completable.fromAction {
            listData = mdb.newsDataDao().getAllNewsData()
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onComplete() {
                    view.handleProgressAlert(false)
                    if(listData!!.isNotEmpty()) {
                        view.newsFetchedDB(listData!!)
                    }
                }

                override fun onError(e: Throwable) {
                    //view.handleProgressAlert(false)
                    e.printStackTrace()
                }
            })
    }

    override fun insertIntoDB(list: List<Article>) {
        Completable.fromAction {
            mdb.newsDataDao().insert(list)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onComplete() {
                    view.handleProgressAlert(false)
                    showToast(context, "Successfully Inserted")
                }

                override fun onError(e: Throwable) {
                    view.handleProgressAlert(false)
                    e.printStackTrace()
                }
            })
    }

}