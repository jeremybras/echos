package fr.echos.articles.domain

import java.time.LocalDateTime

sealed interface ArticleResult {
    data class Success(
        val articles: List<Article>
    ) : ArticleResult
    data class Error(
        val message: String?,
    ) : ArticleResult
}

data class Article(
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val imageUrl: String,
    val publicationDate: LocalDateTime,
)

class ArticleException(message: String) : Exception(message)
