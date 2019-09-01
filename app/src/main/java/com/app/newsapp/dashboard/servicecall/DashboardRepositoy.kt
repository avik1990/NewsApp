package com.app.newsapp.dashboard.servicecall

import android.content.Context
import com.app.newsapp.dashboard.DataSourceCallBack
import com.app.newsapp.dashboard.model.Article
import com.app.newsapp.dashboard.model.NewsResponse
import com.app.newsapp.db.AppDatabse
import com.app.newsapp.db.NewsDao
import com.app.newsapp.utils.showToast
import com.app.others.APIService
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

open class DashboardRepositoy(private val apiService: APIService, private val context: Context) {

    private var mWordDao: NewsDao? = null
    private var listData: List<Article>? = null

    fun callNewsApi(date: String, _publishedAt: String, _apiKeys: String, listener: DataSourceCallBack<NewsResponse>) {
        apiService.getNewsResponse(date, _publishedAt, _apiKeys)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status!! == "ok") {
                    listener.onSuccess(it)
                } else {
                    listener.onError("Please Try Again Later")
                }

            }, {
                listener.onError("Something went wrong")
            })
    }


    fun insertDataRepo(listarticle: List<Article>) {
        val mDb = AppDatabse.getInstance(context)
        mWordDao = mDb!!.newsDataDao()
        Completable.fromAction {
            mWordDao!!.deleteData()
            mWordDao!!.insert(listarticle)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onComplete() {
                    showToast(context, "Successfully Inserted")
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
    }


    fun getDataRepo(): List<Article> {
        val mDb = AppDatabse.getInstance(context)
        mWordDao = mDb!!.newsDataDao()
        listData = mWordDao!!.getAllNewsData()
        return listData!!
    }


}