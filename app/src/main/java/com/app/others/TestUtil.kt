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


}