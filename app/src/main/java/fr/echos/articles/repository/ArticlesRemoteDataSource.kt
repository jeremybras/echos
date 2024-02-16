package fr.echos.articles.repository

import fr.echos.articles.domain.Article
import fr.echos.articles.domain.ArticleException
import retrofit2.Call
import javax.inject.Inject

class ArticlesRemoteDataSource @Inject constructor(
    private val service: EchosService,
    private val errorParser: ErrorParser,
    private val articleResponseConverter: ArticleResponseConverter,
) {

    companion object {
        private const val DOMAIN_SEPARATOR = ","
    }

    @Throws(ArticleException::class)
    fun loadRequest(
        page: Int,
        perPage: Int,
        query: String?,
        domains: List<String>,
    ): List<Article> {
        val request = service.getArticles(
            page = page,
            pageSize = perPage,
            query = query,
            domains = domains.joinToString(separator = DOMAIN_SEPARATOR),
        )
        when (val response = handleRequest(request)) {
            is ArticleResponse.Success -> {
                return articleResponseConverter.convert(response)
            }

            is ArticleResponse.Error -> {
                throw ArticleException(response.message)
            }
        }
    }

    private fun handleRequest(request: Call<ArticleResponse.Success>): ArticleResponse {
        val result = request.execute()
        return if (result.isSuccessful) {
            result.body() ?: ArticleResponse.Error(
                message = "Empty body",
                code = "0",
            )
        } else {
            errorParser.parseError(result.errorBody())
        }
    }
}
