package fr.echos.articles.domain

import fr.echos.articles.repository.ArticlesRepository
import javax.inject.Inject

class ArticlesInteractor @Inject constructor(
    private val repository: ArticlesRepository,
) {
    fun loadArticles(
        page: Int,
        perPage: Int,
        query: String?,
        domains: List<String>,
    ): ArticleResult {
        return try {
            val (articles, articlesNumber) = repository.getArticles(
                page = page,
                perPage = perPage,
                query = query,
                domains = domains,
            )
            ArticleResult.Success(
                articles = articles,
                articlesNumber = articlesNumber,
            )
        } catch (e: ArticleException) {
            ArticleResult.Error(
                message = e.message,
            )
        }
    }
}
