package fr.echos.articles.presentation

import android.content.res.Resources
import fr.echos.R
import fr.echos.articles.domain.Article
import java.net.URI
import javax.inject.Inject

class ArticleTransformer @Inject constructor(
    private val resources: Resources,
    private val dateFormatter: DateFormatter,
) {

    fun transform(articles: List<Article>): List<ArticleDisplayModel> {
        return articles.map { article -> transform(article) }
    }

    private fun transform(article: Article): ArticleDisplayModel {
        val uri = URI(article.url)
        val domain = uri.host
        return ArticleDisplayModel(
            title = article.title,
            description = article.description,
            imageUrl = article.imageUrl,
            domain = domain,
            url = article.url,
            dateAndAuthor = resources.getString(
                R.string.date_author,
                dateFormatter.format(article.publicationDate),
                article.author,
            )
        )
    }
}
