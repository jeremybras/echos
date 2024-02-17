package fr.echos.articles.data

sealed interface ArticleResponse {
    data class Success(
        val totalResults: Int,
        val articles: List<ArticleJson>,
    ) : ArticleResponse

    data class Error(
        val code: String,
        val message: String,
    ) : ArticleResponse
}

data class ArticleJson(
    val source: ArticleSourceJson,
    val author: String?,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String,
)

data class ArticleSourceJson(
    val id: String,
    val name: String,
)
