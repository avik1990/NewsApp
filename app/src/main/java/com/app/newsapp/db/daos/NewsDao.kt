package com.app.newsapp.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.app.newsapp.dashboard.model.Article
/**
 * Created by avik on 25/08/19.
 */
@Dao
interface NewsDao {

    @Query("SELECT * from tbl_newsData")
    fun getAllNewsData(): List<Article>

    @Insert(onConflict = REPLACE)
    fun insert(newsData: List<Article>)

    @Query("DELETE from tbl_newsData")
    fun deleteAll()

    @Query("Select author from tbl_newsData where _id=:insertedID")
    fun findById(insertedID: Long): String

    @Query("DELETE from tbl_newsData where author=:author")
    fun deleteByAuthor(author: String?)

    @Query("DELETE from tbl_newsData")
    fun deleteData()

}