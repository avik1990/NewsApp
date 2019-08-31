package com.app.newsapp.dashboard

import android.content.Context
import com.app.newsapp.dashboard.model.Article
import com.app.newsapp.dashboard.model.NewsResponse
import com.app.newsapp.dashboard.servicecall.DashboardRepositoy
import com.app.newsapp.db.AppDatabse
import com.app.newsapp.db.NewsDao
import com.app.newsapp.utils.showToast
import com.app.others.APIService
import com.app.others.Constants
import junit.framework.Assert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class DashboardPresenterTest {

    @Mock
    private var context: Context? = null

    @Mock
    private var view: DashboardContract.View? = null

    @Mock
    private var apiService: APIService? = null

    @Mock
    private var dashboardRepositoy: DashboardRepositoy? = null

    private var dashboardPresenter: DashboardPresenter? = null

    @Mock
    private var mdb: AppDatabse? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dashboardPresenter = DashboardPresenter(context!!, view!!, dashboardRepositoy!!, mdb!!)
    }

    @Test
    fun testapiCall() {
        Mockito.`when`(view!!.isNetworkAvailable()).thenReturn(true)
        dashboardPresenter!!.callNewsAPI(Constants.Keys._date, Constants.Keys._publishedAt, Constants.Keys._apiKeys)
        Mockito.verify(view, Mockito.times(0))!!.showNetworkUnavailableMsg()
    }

    /* @Test
     fun testNetworkCheck(){
         Mockito.`when`(view!!.isNetworkAvailable()).thenReturn(true)
         dashboardPresenter!!.callNewsAPI(Constants.Keys._date, Constants.Keys._publishedAt, Constants.Keys._apiKeys)
         Mockito.verify(view, Mockito.times(0))!!.showNetworkUnavailableMsg()
     }*/

}