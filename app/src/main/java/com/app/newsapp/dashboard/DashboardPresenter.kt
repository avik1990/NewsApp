package com.app.newsapp.dashboard

import android.content.Context
import android.util.Log
import com.app.newsapp.R
import com.app.newsapp.dashboard.servicecall.DashboardRepositoy
import com.app.newsapp.utils.showToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DashboardPresenter(
    private val context: Context,
    private val view: DashboardContract.View,
    private val dashboardRepositoy: DashboardRepositoy
) :
    DashboardContract.Presenter {

    val TAG = "DashboardPresenter"
    private var disposable: Disposable? = null

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
            view.showNetworkUnavailableMsg()
            return
        }

        //show progressdilaog
        view.handleProgressAlert(true)

        disposable = dashboardRepositoy.callLoginApi("", "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.handleProgressAlert(false)
                if (it.getStatus()!! == "ok") {
                    showToast(context, "" + it.getArticles()!!.size)
                    view.newsFetched(it.getArticles()!!)
                }

                /*if (!it.response.data.isEmpty()) {
                    Log.d("Json_Stirng", json)
                    val obj = JSONObject(AESCrypt.decrypt(Base64.decode(it.response.salt, Base64.NO_WRAP), it.response.data))
                    if (obj.get("responseCode").toString().equals("200")) {
                        // view.showSomeErrorOccurredMsg(obj.get("responseDetails") as String)
                        val loginresponsesucess = GsonBuilder().create().fromJson(json, LoginResponseModel::class.java)
                        Log.d("RawToken", loginresponsesucess.user!!.rememberToken.toString())
                        Log.v("profile_image_login",loginresponsesucess.user!!.mainimage.toString())


                        if (view.isActivityRunning()) {
                            view.goToNextPage()
                        }
                    } else if (obj.get("responseCode").toString().equals("203")) {

                    }
                }*/
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

}