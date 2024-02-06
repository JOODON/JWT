package com.example.springloginform.repository

import com.example.springloginform.model.Article
import org.springframework.stereotype.Repository
import java.util.UUID


@Repository
class ArticleRepository {
    private val articles = listOf(
        Article(id = UUID.randomUUID() , title = "Article 1" , "Content 1"),
        Article(id = UUID.randomUUID() , title = "Article 2" , "Content 2")
    )


    fun findAll() : List<Article> {
        return articles
    }


}