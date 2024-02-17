package fr.echos.articles.presentation

import android.content.res.Resources
import fr.echos.R
import fr.echos.articles.domain.Article
import fr.echos.utils.DateFormatter
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class ArticleTransformer @Inject constructor(
    private val resources: Resources,
    private val dateFormatter: DateFormatter,
) {

    companion object {
        private val DEFAULT_DISPLAY_MODE = DomainDisplayMode.VERTICAL
    }

    fun transform(articles: List<Article>): List<ArticleDisplayModel> {
        return articles.map { article -> transform(article) }
    }

    private fun transform(article: Article): ArticleDisplayModel {

        val uri = URI(article.url)
        val domain = fullDomainList
            .firstOrNull { it.name == uri.host }
            ?: DomainUiData(
                name = uri.host,
                displayMode = DEFAULT_DISPLAY_MODE,
            )

        val encodedUrl = URLEncoder.encode(article.url, StandardCharsets.UTF_8.toString())

        val formattedDate = dateFormatter.format(article.publicationDate)
        return ArticleDisplayModel(
            title = article.title,
            description = article.description,
            imageUrl = article.imageUrl,
            domain = domain,
            encodedUrl = encodedUrl,
            dateAndAuthor = if (article.author.isNullOrBlank().not()) {
                resources.getString(R.string.date_author, formattedDate, article.author)
            } else formattedDate,
        )
    }
}
