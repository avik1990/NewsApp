package com.app.newsapp.db

import android.arch.persistence.room.Room
import android.content.Context
import com.app.others.TestUtil
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import org.mockito.MockitoAnnotations


@RunWith(MockitoJUnitRunner::class)
class NewsDataBaseTest {
    @Mock
    private var newsDao: NewsDao? = null
    @Mock
    private var db: AppDatabse? = null

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val context = mock(Context::class.java)
        db = Room.inMemoryDatabaseBuilder(context, AppDatabse::class.java).build()
        newsDao= db!!.newsDataDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db!!.close()
    }

    @Test
    @Throws(Exception::class)
    fun readAndWriteTests() {
        val note = TestUtil.createNote()
        Completable.fromAction {
            val insertedID = newsDao!!.insert(note) as Long
            assertNotNull(insertedID)

            val inserted = newsDao!!.findById(insertedID)
            assertNotNull(inserted)
            assertEquals(inserted,"Avik")

            val deletedQtd = newsDao!!.deleteByAuthor(inserted) as Long
            assertEquals(deletedQtd, 1)

        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onComplete() {

                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })

    }


}