package fr.echos.articles.data

import fr.echos.articles.domain.Article
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class ArticleResponseConverterTest {

    @InjectMocks
    private lateinit var converter: ArticleResponseConverter

    @Test
    fun `convert - given a successful response - then should return a list of articles`() {
        // Given
        val response = ArticleResponse.Success(
            totalResults = 1,
            articles = listOf(
                ArticleJson(
                    source = ArticleSourceJson(
                        id = "id",
                        name = "name"
                    ),
                    author = "author",
                    title = "title",
                    description = "description",
                    url = "url",
                    urlToImage = "urlToImage",
                    publishedAt = "2021-01-01T00:00:00Z",
                    content = "content",
                )
            )
        )

        // When
        val result = converter.convert(response)

        // Then
        assertEquals(
            listOf(
                Article(
                    author = "author",
                    title = "title",
                    description = "description",
                    url = "url",
                    imageUrl = "urlToImage",
                    publicationDate = LocalDateTime.of(2021, 1, 1, 0, 0, 0)
                )
            ),
            result
        )
    }

}