package com.app.newsapp.dashboard

import android.content.Context
import com.app.newsapp.dashboard.servicecall.DashboardProvider
import com.app.newsapp.db.AppDatabse
import com.app.others.Constants
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class DashboardPresenterTest {

    lateinit var dashboardPresenter: DashboardPresenter

    @Mock
    lateinit var newsDataBaseMock: AppDatabse
    @Mock
    lateinit var contextMock: Context
    @Mock
    lateinit var dashboardViewMock : DashboardContract.View

    @Before
    fun setUp() {
        dashboardViewMock = Mockito.mock(DashboardContract.View::class.java)
        contextMock = Mockito.mock(Context::class.java)
        newsDataBaseMock = Mockito.mock(AppDatabse::class.java)
        dashboardPresenter = DashboardPresenter(contextMock, dashboardViewMock, DashboardProvider.getNewsRepository(contextMock))
    }

    @Test
    fun testNetworkCheckSuccess(){
        Mockito.`when`(dashboardViewMock.isNetworkAvailable()).thenReturn(false)
        dashboardPresenter.callNewsAPI(Constants.Keys._date, Constants.Keys._publishedAt, Constants.Keys._apiKeys)
        Mockito.verify(dashboardViewMock, Mockito.times(1)).showNetworkUnavailableMsg()
    }

    @Test
    fun testNetworkCheck(){
        Mockito.`when`(dashboardViewMock.isNetworkAvailable()).thenReturn(true)
        dashboardPresenter.callNewsAPI(Constants.Keys._date, Constants.Keys._publishedAt, Constants.Keys._apiKeys)
        Mockito.verify(dashboardViewMock, Mockito.times(0)).showNetworkUnavailableMsg()
    }

    @Test
    fun testAPICall(){
        Mockito.`when`(dashboardViewMock.isNetworkAvailable()).thenReturn(true)
        dashboardPresenter.callNewsAPI(Constants.Keys._date, Constants.Keys._publishedAt, Constants.Keys._apiKeys)
        Mockito.verify(dashboardViewMock, Mockito.times(0)).showNetworkUnavailableMsg()
    }


    /*@Test
    fun testDataBaseCheck(){
        Mockito.`when`(dashboardViewMock.isNetworkAvailable()).thenReturn(false)
        dashboardPresenter.getDataFromDB()
        Mockito.verify(dashboardViewMock, Mockito.times(0)).showNetworkUnavailableMsg()
    }*/

}