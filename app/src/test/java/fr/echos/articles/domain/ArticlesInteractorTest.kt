package fr.echos.articles.domain

import fr.echos.articles.data.ArticlesRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ArticlesInteractorTest {

    @Mock
    private lateinit var repository: ArticlesRepository

    @InjectMocks
    private lateinit var interactor: ArticlesInteractor

    @Test
    fun `loadArticles - should call repository and pass its result to the caller`() {
        // Given
        val article = mock(Article::class.java)
        given(
            repository.getArticles(
                page = 1,
                perPage = 20,
                query = "query",
                domains = listOf("domain"),
            )
        ).willReturn(listOf(article) to 20)

        // When
        val result = interactor.loadArticles(
            page = 1,
            perPage = 20,
            query = "query",
            domains = listOf("domain"),
        )

        // Then
        assertEquals(
            ArticleResult.Success(
                articles = listOf(article),
                articlesNumber = 20,
            ),
            result
        )
    }

    @Test
    fun `loadArticles - should throw an exception if the repository throws an exception`() {
        // Given
        val exception = ArticleException("error")
        given(
            repository.getArticles(
                page = 1,
                perPage = 20,
                query = "query",
                domains = listOf("domain"),
            )
        ).willThrow(exception)

        // When
        val result = interactor.loadArticles(
            page = 1,
            perPage = 20,
            query = "query",
            domains = listOf("domain"),
        )

        // Then
        assertEquals(
            ArticleResult.Error(
                message = "error",
            ),
            result
        )
    }

}
