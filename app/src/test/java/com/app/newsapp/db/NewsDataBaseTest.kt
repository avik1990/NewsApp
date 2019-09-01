package com.app.newsapp.db

import android.arch.persistence.room.Room
import android.content.Context
import com.app.newsapp.dashboard.model.Article
import com.app.others.TestUtil
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import java.io.IOException
import org.mockito.MockitoAnnotations


class NewsDataBaseTest {

    @Mock
    lateinit var newsDao: NewsDao
    @Mock
    lateinit var db: AppDatabse
    @Mock
    lateinit var contextMock: Context

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        newsDao = Mockito.mock(NewsDao::class.java)
        contextMock = Mockito.mock(Context::class.java)

        db = Room.inMemoryDatabaseBuilder(contextMock, AppDatabse::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertCheck() {
        val note = TestUtil.createNote()
        val insertedID = newsDao.insert(note)
        assertNotNull(insertedID)
    }

    @Test
    @Throws(Exception::class)
    fun fetchAllDataCheck() {
        val note = listOf(
            Article(
                null, "Author1", "Title1", "Description1",
                "URL1", "URLTOIMAGE1", "12", "content1", null
            )
        )

        Mockito.`when`(newsDao.insert(note)).then {
            var allWords = newsDao.getAllNewsData()
            assertEquals(allWords[0].author, "Author1")
        }

        val note1 = listOf(
            Article(
                null, "AuthorTest22", "Title22", "Description222",
                "UR2L2", "URLTOIMAGE2211", "1212222", "co222ntent", null
            )
        )


        Mockito.`when`(newsDao.insert(note1)).then {
            var allWords = newsDao.getAllNewsData()
            assertEquals(allWords[0].author, "AuthorTest22")
        }
    }



    @Test
    @Throws(Exception::class)
    fun deleteDataCheck() {
        val note = listOf(
            Article(
                null, "Author1", "Title1", "Description1",
                "URL1", "URLTOIMAGE1", "12", "content1", null
            )
        )

        Mockito.`when`(newsDao.insert(note)).then {
            newsDao.deleteData()
            var allWords = newsDao.getAllNewsData()
            assertTrue(allWords.isEmpty())

        }

    }



    /*@Test
    @Throws(Exception::class)
    fun readAndWriteTests() {
        val note = TestUtil.createNote()
        val insertedID = newsDao!!.insert(note) as Long

        assertNotNull(insertedID)

        val inserted = newsDao!!.findById(insertedID)
        assertNotNull(inserted)
        assertEquals(inserted, "Avik")

        val deletedQtd = newsDao!!.deleteByAuthor(inserted) as Long
        assertEquals(deletedQtd, 1)
        *//*Completable.fromAction {


        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onComplete() {

                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })*//*

    }*/


}