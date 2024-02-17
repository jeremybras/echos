package fr.echos.articles.data

import fr.echos.articles.domain.Article
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject


class ArticleResponseConverter @Inject constructor() {

    companion object {
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    }

    fun convert(response: ArticleResponse.Success): List<Article> {
        return response.articles.map {
            Article(
                author = it.author,
                title = it.title,
                description = it.description,
                url = it.url,
                imageUrl = it.urlToImage,
                publicationDate = LocalDateTime.parse(it.publishedAt, formatter),
            )
        }
    }
}
