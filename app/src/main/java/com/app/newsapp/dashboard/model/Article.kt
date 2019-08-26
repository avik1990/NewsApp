package com.app.newsapp.dashboard.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "tbl_newsData")
data class Article(
    @PrimaryKey(autoGenerate = true) var _id: Long?,
    @ColumnInfo(name = "src_id") var src_id: String?,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "author") var author: String?,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "url") var url: String?,
    @ColumnInfo(name = "urlToImage") var urlToImage: String?,
    @ColumnInfo(name = "publishedAt") var publishedAt: String?,
    @ColumnInfo(name = "content") var content: String?,
    @Ignore var source: Source?
){
    constructor() : this(null,"", "", "", "", "",
        "", "", "", "",null)
}