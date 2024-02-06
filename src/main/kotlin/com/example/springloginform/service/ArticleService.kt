package com.example.springloginform.service

import com.example.springloginform.model.Article
import com.example.springloginform.repository.ArticleRepository
import org.springframework.stereotype.Service

@Service
class ArticleService(
    private val articleRepository: ArticleRepository
) {

    fun findAll() : List<Article> = articleRepository.findAll()


}