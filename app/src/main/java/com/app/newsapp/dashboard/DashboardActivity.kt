package com.app.newsapp.dashboard

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.app.newsapp.R
import com.app.newsapp.dashboard.adapter.NewsAdapter
import com.app.newsapp.dashboard.model.NewsResponse
import com.app.newsapp.dashboard.servicecall.DashboardProvider
import com.app.newsapp.utils.isConnectedToNetwork
import com.app.newsapp.utils.showSnackbar
import com.app.others.BaseActivity
import com.app.others.LoaderDialog
import kotlinx.android.synthetic.main.activity_main.*

class DashboardActivity : BaseActivity(), DashboardContract.View, NewsAdapter.onRowItemSelected {

    lateinit var dashboardPresenter: DashboardPresenter
    lateinit var context: Context
    lateinit var newsAdapter: NewsAdapter

    private val loader by lazy {
        LoaderDialog(this)
    }

    override fun onDestroy() {
        dashboardPresenter.stop()
        super.onDestroy()
    }

    override fun getContext(): AppCompatActivity {
        return this@DashboardActivity
    }

    override fun handleProgressAlert(showingStatus: Boolean) {
        if (showingStatus) {
            loader.show()
        } else {
            loader.hide()
        }
    }

    override fun goToNextPage() {
    }

    override fun isActivityRunning(): Boolean {
        return isActivityVisible
    }

    override fun setPresenter(presenter: DashboardContract.Presenter) {
        dashboardPresenter = presenter as DashboardPresenter
    }

    override fun isNetworkAvailable(): Boolean {
        return isConnectedToNetwork(this@DashboardActivity)
    }

    override fun showNetworkUnavailableMsg() {
        showSnackbar(llDashParent, getString(R.string.networkUnavailable), 3000)
    }

    override fun showSomeErrorOccurredMsg(msg: String) {
        showSnackbar(llDashParent, msg, 3000)
    }

    override fun initResources() {
        context = this
        DashboardPresenter(context, this, DashboardProvider.getNewsRepository()).start()
    }

    override fun initListeners() {
        dashboardPresenter.callNewsAPI("")
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun newsFetched(list: MutableList<NewsResponse.Article>) {
        newsAdapter = NewsAdapter(context!!, list, this)
        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rcy_news.layoutManager = mLayoutManager
        rcy_news.itemAnimator = DefaultItemAnimator()
        rcy_news.adapter = newsAdapter
    }

    override fun getPosition(pos: Int) {
    }

}
