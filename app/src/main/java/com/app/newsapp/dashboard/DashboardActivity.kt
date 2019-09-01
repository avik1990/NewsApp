package com.app.newsapp.dashboard

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.app.newsapp.R
import com.app.newsapp.dashboard.adapter.NewsAdapter
import com.app.newsapp.dashboard.model.Article
import com.app.newsapp.dashboard.servicecall.DashboardProvider
import com.app.newsapp.details.DetailsActivity
import com.app.newsapp.utils.isConnectedToNetwork
import com.app.newsapp.utils.showSnackbar
import com.app.others.BaseActivity
import com.app.others.Constants
import com.app.others.LoaderDialog
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.app.ActivityOptionsCompat
import android.widget.ImageView


class DashboardActivity : BaseActivity(), DashboardContract.View, NewsAdapter.onRowItemSelected {


    lateinit var dashboardPresenter: DashboardPresenter
    lateinit var context: Context
    lateinit var newsAdapter: NewsAdapter
    lateinit var listNews: List<Article>
    lateinit var jsonbject: String
    lateinit var imageView: ImageView

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


    override fun shoDialog() {
        loader.show()
    }

    override fun dismissDialog() {
        loader.hide()
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
        DashboardPresenter(context, this, DashboardProvider.getNewsRepository(context)).start()
    }

    override fun initListeners() {
        dashboardPresenter.callNewsAPI(Constants.Keys._date, Constants.Keys._publishedAt, Constants.Keys._apiKeys)
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun newsFetched(list: List<Article>) {
        listNews = list
        inflateAdapter(listNews)
    }

    private fun inflateAdapter(listNews: List<Article>) {
        newsAdapter = NewsAdapter(context, listNews, this)
        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rcy_news.layoutManager = mLayoutManager
        rcy_news.itemAnimator = DefaultItemAnimator()
        rcy_news.adapter = newsAdapter
    }

    override fun getPosition(pos: Int, imageView: ImageView) {
        val gson = Gson()
        gson.toJson(listNews[pos])
        jsonbject = gson.toJson(listNews[pos])
        this.imageView=imageView
        goToNextPage()
    }

    override fun goToNextPage() {
        val activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView, "imageMain")
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra(Constants.Keys._jsonString, jsonbject)
        startActivity(intent, activityOptionsCompat.toBundle())
    }

    override fun newsFetchedDB(list: List<Article>) {
        listNews = list
        inflateAdapter(listNews)
    }

}
