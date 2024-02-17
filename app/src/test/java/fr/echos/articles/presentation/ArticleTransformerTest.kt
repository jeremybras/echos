package fr.echos.articles.presentation

import android.content.res.Resources
import fr.echos.R
import fr.echos.articles.domain.Article
import fr.echos.utils.DateFormatter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class ArticleTransformerTest {

    @Mock
    private lateinit var resources: Resources

    @Mock
    private lateinit var dateFormatter: DateFormatter

    @InjectMocks
    private lateinit var articleTransformer: ArticleTransformer

    @Test
    fun `transform should return a list of ArticleDisplayModel`() {
        // Given
        val article = Article(
            title = "title",
            description = "description",
            imageUrl = "imageUrl",
            url = "http://techcrunch.com",
            publicationDate = LocalDateTime.of(2021, 1, 1, 0, 0),
            author = "author",
        )
        val articles = listOf(article)

        given(dateFormatter.format(article.publicationDate)).willReturn("publicationDate")
        given(
            resources.getString(
                R.string.date_author,
                "publicationDate",
                "author",
            )
        ).willReturn("date_author")

        // When
        val result = articleTransformer.transform(articles)

        // Then
        assertEquals(
            listOf(
                ArticleDisplayModel(
                    title = "title",
                    description = "description",
                    imageUrl = "imageUrl",
                    domain = DomainUiData(
                        name = "techcrunch.com",
                        displayMode = DomainDisplayMode.HORIZONTAL,
                        isSelected = true,
                    ),
                    encodedUrl = "http%3A%2F%2Ftechcrunch.com",
                    dateAndAuthor = "date_author",
                )
            ),
            result
        )
    }

    @Test
    fun `transform - given no found domain in domain list and no author - should return a list of ArticleDisplayModel`() {
        // Given
        val article = Article(
            title = "title",
            description = "description",
            imageUrl = "imageUrl",
            url = "http://google.com",
            publicationDate = LocalDateTime.of(2021, 1, 1, 0, 0),
            author = null,
        )
        val articles = listOf(article)

        given(dateFormatter.format(article.publicationDate)).willReturn("publicationDate")

        // When
        val result = articleTransformer.transform(articles)

        // Then
        assertEquals(
            listOf(
                ArticleDisplayModel(
                    title = "title",
                    description = "description",
                    imageUrl = "imageUrl",
                    domain = DomainUiData(
                        name = "google.com",
                        displayMode = DomainDisplayMode.VERTICAL
                    ),
                    encodedUrl = "http%3A%2F%2Fgoogle.com",
                    dateAndAuthor = "publicationDate"
                )
            ),
            result
        )
    }
}
