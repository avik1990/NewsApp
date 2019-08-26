package com.app.newsapp.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Room
import android.content.Context
import com.app.newsapp.dashboard.model.Article

/**
 * Created by avik on 25/08/19.
 */

@Database(entities = arrayOf(Article::class), version = 1)
abstract class NewsDataBase : RoomDatabase() {

    abstract fun weatherDataDao(): NewsDataDao

    companion object {
        private var INSTANCE: NewsDataBase? = null

        fun getInstance(context: Context): NewsDataBase? {
            if (INSTANCE == null) {
                synchronized(NewsDataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        NewsDataBase::class.java, "news.db"
                    )
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}