package fr.echos.articles.data

import fr.echos.articles.domain.Article
import fr.echos.articles.domain.ArticleException
import javax.inject.Inject

class ArticlesRepository @Inject constructor(
    private val dataSource: ArticlesRemoteDataSource,
) {
    @Throws(ArticleException::class)
    fun getArticles(
        page: Int,
        perPage: Int,
        query: String?,
        domains: List<String>,
    ): Pair<List<Article>, Int> {
        return dataSource.loadRequest(
            page = page,
            perPage = perPage,
            query = query,
            domains = domains,
        )
    }
}
