package com.app.newsapp.dashboard.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsResponse {

    @SerializedName("status")
    @Expose
    private var status: String? = null
    @SerializedName("totalResults")
    @Expose
    private var totalResults: Int? = null
    @SerializedName("articles")
    @Expose
    private var articles: List<Article>? = null

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }

    fun getTotalResults(): Int? {
        return totalResults
    }

    fun setTotalResults(totalResults: Int?) {
        this.totalResults = totalResults
    }

    fun getArticles(): List<Article>? {
        return articles
    }

    fun setArticles(articles: List<Article>) {
        this.articles = articles
    }

    inner class Article {

        @SerializedName("source")
        @Expose
        var source: Source? = null
        @SerializedName("author")
        @Expose
        var author: String? = null
        @SerializedName("title")
        @Expose
        var title: String? = null
        @SerializedName("description")
        @Expose
        var description: String? = null
        @SerializedName("url")
        @Expose
        var url: String? = null
        @SerializedName("urlToImage")
        @Expose
        var urlToImage: String? = null
        @SerializedName("publishedAt")
        @Expose
        var publishedAt: String? = null
        @SerializedName("content")
        @Expose
        var content: String? = null
    }

    inner class Source {

        @SerializedName("id")
        @Expose
        var id: Any? = null
        @SerializedName("name")
        @Expose
        var name: String? = null
    }

}