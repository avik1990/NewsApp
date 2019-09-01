package com.app.others

import com.app.newsapp.dashboard.model.Article

object TestUtil {

    fun createNote(): List<Article> {

        return listOf(
            Article(
                null, "Author", "Title", "Description",
                "URL", "URLTOIMAGE", "12", "content", null
            )
        )
    }

    fun createNote1(): List<Article> {

        return listOf(
            Article(
                null, "AuthorTest", "Title", "Description",
                "URL", "URLTOIMAGE", "12", "content", null
            )
        )
    }


}