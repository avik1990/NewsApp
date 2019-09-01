package com.app.newsapp.dashboard

import android.content.Context
import android.util.Log
import com.app.newsapp.dashboard.model.Article
import com.app.newsapp.dashboard.model.NewsResponse
import com.app.newsapp.dashboard.servicecall.DashboardProvider
import com.app.newsapp.dashboard.servicecall.DashboardRepositoy
import com.app.newsapp.db.AppDatabse
import com.app.others.Constants
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class DashboardPresenterTest {

    lateinit var dashboardPresenter: DashboardPresenter

    @Mock
    lateinit var newsDataBaseMock: AppDatabse
    @Mock
    lateinit var responseMock: NewsResponse
    @Mock
    lateinit var repoMock: DashboardRepositoy
    @Mock
    lateinit var articleMock: Article
    @Mock
    lateinit var contextMock: Context
    @Mock
    lateinit var dashboardViewMock : DashboardContract.View

    @Before
    fun setUp() {
        dashboardViewMock = Mockito.mock(DashboardContract.View::class.java)
        contextMock = Mockito.mock(Context::class.java)
        articleMock = Mockito.mock(Article::class.java)
        repoMock = Mockito.mock(DashboardRepositoy::class.java)
        responseMock = Mockito.mock(NewsResponse::class.java)
        newsDataBaseMock = Mockito.mock(AppDatabse::class.java)
        dashboardPresenter = DashboardPresenter(contextMock, dashboardViewMock, DashboardProvider.getNewsRepository(contextMock))
    }

    @Test
    fun testNetworkCheckSuccess(){
        Mockito.`when`(dashboardViewMock.isNetworkAvailable()).thenReturn(true)
        dashboardPresenter.callNewsAPI(Constants.Keys._date, Constants.Keys._publishedAt, Constants.Keys._apiKeys)
        Mockito.verify(dashboardViewMock, Mockito.times(0)).showNetworkUnavailableMsg()
        Mockito.verify(dashboardViewMock, Mockito.times(1)).shoDialog()
    }

    @Test
    fun testNetworkCheckFailure(){
        Mockito.`when`(dashboardViewMock.isNetworkAvailable()).thenReturn(false)
        dashboardPresenter.callNewsAPI(Constants.Keys._date, Constants.Keys._publishedAt, Constants.Keys._apiKeys)
        Mockito.verify(dashboardViewMock, Mockito.times(1)).showNetworkUnavailableMsg()
    }

    @Test
    fun testDataBaseCheck(){
        Mockito.`when`(dashboardViewMock.isNetworkAvailable()).thenReturn(false)
        dashboardPresenter.getDataFromDB()
        Mockito.verify(dashboardViewMock, Mockito.times(0)).showNetworkUnavailableMsg()
    }

    @Test
    fun testAPISuccess(){
        Mockito.`when`(dashboardViewMock.isNetworkAvailable()).thenReturn(true)
        dashboardPresenter.callNewsAPI("2019-08-24", "publishedAt", "4eb1483620174fba970d74a1cd7e300f")
        Mockito.verify(dashboardViewMock, Mockito.times(1)).shoDialog()
        Mockito.verify(dashboardViewMock, Mockito.times(0)).showNetworkUnavailableMsg()
        repoMock.callNewsApi("2019-08-24", "publishedAt", "4eb1483620174fba970d74a1cd7e300f", object : DataSourceCallBack<NewsResponse> {
            override fun onSuccess(responseData: NewsResponse) {
                Log.e("testAPISuccess","onSuccess")
                Mockito.verify(dashboardViewMock, Mockito.times(1)).dismissDialog()
                Mockito.verify(dashboardViewMock, Mockito.times(1)).newsFetched(responseMock.articles)
            }

            override fun onError(errorMsg: String) {
                Log.e("testAPISuccess","onError")
                Mockito.verify(dashboardViewMock, Mockito.times(0)).dismissDialog()
                Mockito.verify(dashboardViewMock, Mockito.times(0)).showSomeErrorOccurredMsg("")
            }

        })
    }

    @Test
    fun testAPIFailure(){
        Mockito.`when`(dashboardViewMock.isNetworkAvailable()).thenReturn(true)
        dashboardPresenter.callNewsAPI("", "", "")
        Mockito.verify(dashboardViewMock, Mockito.times(1)).shoDialog()
        Mockito.verify(dashboardViewMock, Mockito.times(0)).showNetworkUnavailableMsg()
        repoMock.callNewsApi("", "", "", object : DataSourceCallBack<NewsResponse> {
            override fun onSuccess(responseData: NewsResponse) {
                Mockito.verify(dashboardViewMock, Mockito.times(0)).dismissDialog()
                Mockito.verify(dashboardViewMock, Mockito.times(0)).newsFetched(responseMock.articles)
            }

            override fun onError(errorMsg: String) {
                Mockito.verify(dashboardViewMock, Mockito.times(1)).dismissDialog()
                Mockito.verify(dashboardViewMock, Mockito.times(1)).showSomeErrorOccurredMsg("")
            }

        })
    }

}