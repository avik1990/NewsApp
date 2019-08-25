package com.app.newsapp.details

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.app.newsapp.R
import com.app.newsapp.dashboard.model.NewsResponse
import com.app.newsapp.utils.getFormattedDate
import com.app.others.BaseActivity
import com.app.others.Constants
import com.app.others.LoaderDialog
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : BaseActivity(), DetailsContract.View, DetailsContract {

    lateinit var context: Context
    lateinit var _jsonString: String

    private val loader by lazy {
        LoaderDialog(this)
    }


    override fun getContext(): AppCompatActivity {
        return this@DetailsActivity
    }

    override fun handleProgressAlert(showingStatus: Boolean) {
        if (showingStatus) {
            loader.show()
        } else {
            loader.hide()
        }
    }


    override fun initResources() {
        context = this
        _jsonString = intent.getStringExtra(Constants.Keys._jsonString)
        newsDetailsFetched()
    }

    override fun initListeners() {
    }

    override fun getLayout(): Int {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        )
        return R.layout.activity_details
    }

    override fun newsDetailsFetched() {
        val newsresponse = GsonBuilder().create().fromJson(_jsonString, NewsResponse.Article::class.java)
        news_title.text = newsresponse.title
        tv_source.text = newsresponse.source!!.name

        tv_date.text = getFormattedDate(newsresponse.publishedAt!!)
        news_desc.text = newsresponse.description
        if (!newsresponse.urlToImage.isNullOrBlank()) {
            Picasso.get()
                .load(newsresponse.urlToImage)
                .resize(1420, 800)
                .onlyScaleDown()
                .into(iv_background)
        }

        iv_background
    }
}
