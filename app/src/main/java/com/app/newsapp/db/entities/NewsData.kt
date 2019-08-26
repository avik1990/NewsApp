package com.app.newsapp.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by avik on 25/08/19.
 */
@Entity(tableName = "tbl_newsData")
data class NewsData1(
    @PrimaryKey(autoGenerate = true) var _id: Long?,
    @ColumnInfo(name = "src_id") var src_id: String?,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "author") var author: String?,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "url") var url: String?,
    @ColumnInfo(name = "urlToImage") var urlToImage: String?,
    @ColumnInfo(name = "publishedAt") var publishedAt: String?,
    @ColumnInfo(name = "content") var content: String?
) {
    constructor() : this(null,"", "", "", "", "",
        "", "", "", "")
}