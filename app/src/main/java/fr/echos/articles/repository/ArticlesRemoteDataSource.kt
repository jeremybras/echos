package fr.echos.articles.repository

import fr.echos.articles.domain.Article
import fr.echos.articles.domain.ArticleException
import retrofit2.Call
import java.net.SocketTimeoutException
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
    ): Pair<List<Article>, Int> {

        // For testing purposes
        Thread.sleep(2000)

        val request = service.getArticles(
            page = page,
            pageSize = perPage,
            query = query,
            domains = domains.joinToString(separator = DOMAIN_SEPARATOR),
        )
        println("url = ${request.request().url()}")
        try {
            when (val response = handleRequest(request)) {
                is ArticleResponse.Success -> {
                    val articles = articleResponseConverter.convert(response)
                    return articles to response.totalResults
                }

                is ArticleResponse.Error -> {
                    throw ArticleException(response.message)
                }
            }
        } catch (e: SocketTimeoutException) {
            throw throw ArticleException("Socket timeout")
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
