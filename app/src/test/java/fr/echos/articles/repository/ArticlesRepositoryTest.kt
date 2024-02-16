package fr.echos.articles.repository

import fr.echos.articles.domain.Article
import fr.echos.articles.domain.ArticleException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ArticlesRepositoryTest {

    @Mock
    private lateinit var dataSource: ArticlesRemoteDataSource

    @InjectMocks
    private lateinit var repository: ArticlesRepository

    @Test
    fun `getArticles - should call datasource and pass its result to the caller`() {
        // Given
        val article = mock(Article::class.java)
        given(
            dataSource.loadRequest(
                page = 1,
                perPage = 20,
                query = "query",
                domains = listOf("domain"),
            )
        ).willReturn(listOf(article))

        // When
        val result = repository.getArticles(
            page = 1,
            perPage = 20,
            query = "query",
            domains = listOf("domain"),
        )

        // Then
        assertEquals(listOf(article), result)
    }

    @Test
    fun `getArticles - should throw an exception if the datasource throws an exception`() {
        // Given
        val exception = ArticleException("error")
        given(
            dataSource.loadRequest(
                page = 1,
                perPage = 20,
                query = "query",
                domains = listOf("domain"),
            )
        ).willThrow(exception)

        // When / Then
        assertThrows(ArticleException::class.java) {
            repository.getArticles(
                page = 1,
                perPage = 20,
                query = "query",
                domains = listOf("domain"),
            )
        }
    }
}
