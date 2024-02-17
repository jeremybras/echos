package fr.echos.articles.presentation

import android.content.res.Resources
import fr.echos.R
import fr.echos.articles.domain.Article
import fr.echos.articles.domain.ArticleResult
import fr.echos.articles.domain.ArticlesInteractor
import fr.echos.testutils.BaseCoroutinesTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.BDDMockito.times
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockitoExtension::class)
class ArticlesViewModelTest : BaseCoroutinesTest() {

    @Mock
    private lateinit var interactor: ArticlesInteractor

    @Mock
    private lateinit var articleTransformer: ArticleTransformer

    @Mock
    private lateinit var resources: Resources

    private lateinit var viewModel: ArticlesViewModel

    @BeforeEach
    override fun setUp() {
        viewModel = ArticlesViewModel(
            dispatcher = testDispatcher,
            interactor = interactor,
            articleTransformer = articleTransformer,
            resources = resources,
        )
    }

    @Test
    fun `init - given a Success interactor result - then uiState should be Success`() {
        // Given
        val articles = listOf(mock(Article::class.java))
        given(
            interactor.loadArticles(
                page = 1,
                perPage = 20,
                query = null,
                domains = listOf(
                    "bbc.co.uk",
                    "techcrunch.com",
                ),
            )
        ).willReturn(
            ArticleResult.Success(
                articles = articles,
                articlesNumber = 3020,
            )
        )

        val articleDisplayModel = mock(ArticleDisplayModel::class.java)
        given(articleTransformer.transform(articles)).willReturn(listOf(articleDisplayModel))

        given(
            resources.getQuantityString(
                R.plurals.total_number_of_results,
                3020,
                3020,
            )
        ).willReturn("3020 results")

        // When
        // Init call
        scheduler.advanceUntilIdle()

        // Then
        assertEquals(
            false,
            viewModel.hasNoMorePages.value
        )
        assertEquals(
            "3020 results",
            viewModel.totalNumberOfResults.value
        )
        assertEquals(
            ArticlesUiState.Success(
                articles = listOf(articleDisplayModel),
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun `init - given a Success interactor result and total number of articles 20 - then hasNoMorePages should be true`() {
        // Given
        val articles = listOf(mock(Article::class.java))
        given(
            interactor.loadArticles(
                page = 1,
                perPage = 20,
                query = null,
                domains = listOf(
                    "bbc.co.uk",
                    "techcrunch.com",
                ),
            )
        ).willReturn(
            ArticleResult.Success(
                articles = articles,
                articlesNumber = 20,
            )
        )

        val articleDisplayModel = mock(ArticleDisplayModel::class.java)
        given(articleTransformer.transform(articles)).willReturn(listOf(articleDisplayModel))

        given(
            resources.getQuantityString(
                R.plurals.total_number_of_results,
                20,
                20,
            )
        ).willReturn("20 results")

        // When
        // Init call
        scheduler.advanceUntilIdle()

        // Then
        assertEquals(
            true,
            viewModel.hasNoMorePages.value
        )
        assertEquals(
            "20 results",
            viewModel.totalNumberOfResults.value
        )
        assertEquals(
            ArticlesUiState.Success(
                articles = listOf(articleDisplayModel),
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun `init - given an Error interactor result - then uiState Error`() {
        // Given
        given(
            interactor.loadArticles(
                page = 1,
                perPage = 20,
                query = null,
                domains = listOf(
                    "bbc.co.uk",
                    "techcrunch.com",
                ),
            )
        ).willReturn(
            ArticleResult.Error(
                message = "Mocked response message",
            )
        )

        // When
        // Init call
        scheduler.advanceUntilIdle()

        // Then
        assertEquals(
            ArticlesUiState.Error(
                message = "Mocked response message",
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun `onDomainSelected - should call interactor twice`() {
        // Given
        val articles = listOf(mock(Article::class.java))
        given(
            interactor.loadArticles(
                page = 1,
                perPage = 20,
                query = null,
                domains = listOf(
                    "bbc.co.uk",
                    "techcrunch.com",
                ),
            )
        ).willReturn(
            ArticleResult.Success(
                articles = articles,
                articlesNumber = 3020,
            )
        )
        given(
            interactor.loadArticles(
                page = 1,
                perPage = 20,
                query = null,
                domains = listOf(
                    "techcrunch.com",
                ),
            )
        ).willReturn(
            ArticleResult.Success(
                articles = articles,
                articlesNumber = 3020,
            )
        )

        val articleDisplayModel = mock(ArticleDisplayModel::class.java)
        given(articleTransformer.transform(articles)).willReturn(listOf(articleDisplayModel))

        given(
            resources.getQuantityString(
                R.plurals.total_number_of_results,
                3020,
                3020,
            )
        ).willReturn("3020 results")

        // When
        // Init call
        scheduler.advanceUntilIdle()
        viewModel.onDomainSelected(
            domain = DomainUiData(
                name = "bbc.co.uk",
                displayMode = DomainDisplayMode.HORIZONTAL,
                isSelected = false,
            )
        )
        scheduler.advanceUntilIdle()

        // Then
        then(interactor).should().loadArticles(
            page = 1,
            perPage = 20,
            query = null,
            domains = listOf(
                "techcrunch.com",
            )
        )
        then(interactor).should().loadArticles(
            page = 1,
            perPage = 20,
            query = null,
            domains = listOf(
                "bbc.co.uk",
                "techcrunch.com",
            )
        )
    }

    @Test
    fun `onQueryChange - should call interactor three times`() {
        // Given
        val articles = listOf(mock(Article::class.java))
        given(
            interactor.loadArticles(
                page = 1,
                perPage = 20,
                query = null,
                domains = listOf(
                    "bbc.co.uk",
                    "techcrunch.com",
                ),
            )
        ).willReturn(
            ArticleResult.Success(
                articles = articles,
                articlesNumber = 3020,
            )
        )
        given(
            interactor.loadArticles(
                page = 1,
                perPage = 20,
                query = "google",
                domains = listOf(
                    "bbc.co.uk",
                    "techcrunch.com",
                ),
            )
        ).willReturn(
            ArticleResult.Success(
                articles = articles,
                articlesNumber = 3020,
            )
        )

        val articleDisplayModel = mock(ArticleDisplayModel::class.java)
        given(articleTransformer.transform(articles)).willReturn(listOf(articleDisplayModel))

        given(
            resources.getQuantityString(
                R.plurals.total_number_of_results,
                3020,
                3020,
            )
        ).willReturn("3020 results")

        // When
        // Init call
        scheduler.advanceUntilIdle()
        viewModel.onQueryChange(
            query = "google",
        )
        scheduler.advanceUntilIdle()
        viewModel.onQueryChange(
            query = "",
        )
        scheduler.advanceUntilIdle()

        // Then
        then(interactor).should().loadArticles(
            page = 1,
            perPage = 20,
            query = "google",
            domains = listOf(
                "bbc.co.uk",
                "techcrunch.com",
            )
        )
        then(interactor).should(times(2)).loadArticles(
            page = 1,
            perPage = 20,
            query = null,
            domains = listOf(
                "bbc.co.uk",
                "techcrunch.com",
            )
        )
    }

    @Test
    fun `retry - interactor should be called twice`() {
        // Given
        val articles = listOf(mock(Article::class.java))
        given(
            interactor.loadArticles(
                page = 1,
                perPage = 20,
                query = null,
                domains = listOf(
                    "bbc.co.uk",
                    "techcrunch.com",
                ),
            )
        ).willReturn(
            ArticleResult.Success(
                articles = articles,
                articlesNumber = 3020,
            )
        )

        val articleDisplayModel = mock(ArticleDisplayModel::class.java)
        given(articleTransformer.transform(articles)).willReturn(listOf(articleDisplayModel))

        given(
            resources.getQuantityString(
                R.plurals.total_number_of_results,
                3020,
                3020,
            )
        ).willReturn("3020 results")

        // When
        // Init call
        scheduler.advanceUntilIdle()
        viewModel.retry()
        scheduler.advanceUntilIdle()

        // Then
        then(interactor).should(times(2)).loadArticles(
            page = 1,
            perPage = 20,
            query = null,
            domains = listOf(
                "bbc.co.uk",
                "techcrunch.com",
            )
        )
    }

    @Test
    fun `loadMore - given loadMore multiple times - then values should be updated`() {
        // Given
        val articles = listOf(mock(Article::class.java))
        given(
            interactor.loadArticles(
                page = 1,
                perPage = 20,
                query = null,
                domains = listOf(
                    "bbc.co.uk",
                    "techcrunch.com",
                ),
            )
        ).willReturn(
            ArticleResult.Success(
                articles = articles,
                articlesNumber = 40,
            )
        )
        given(
            interactor.loadArticles(
                page = 2,
                perPage = 20,
                query = null,
                domains = listOf(
                    "bbc.co.uk",
                    "techcrunch.com",
                ),
            )
        ).willReturn(
            ArticleResult.Success(
                articles = articles,
                articlesNumber = 40,
            )
        )

        val articleDisplayModel = mock(ArticleDisplayModel::class.java)
        given(articleTransformer.transform(articles)).willReturn(listOf(articleDisplayModel))

        given(
            resources.getQuantityString(
                R.plurals.total_number_of_results,
                40,
                40,
            )
        ).willReturn("40 results")

        // When / Then
        // Init call
        scheduler.advanceUntilIdle()
        assertEquals(
            false,
            viewModel.hasNoMorePages.value
        )
        assertEquals(
            ArticlesUiState.Success(
                articles = listOf(articleDisplayModel),
            ),
            viewModel.uiState.value
        )

        viewModel.loadMore()
        scheduler.advanceUntilIdle()
        assertEquals(
            true,
            viewModel.hasNoMorePages.value
        )
        assertEquals(
            ArticlesUiState.Success(
                articles = listOf(
                    articleDisplayModel,
                    articleDisplayModel,
                ),
            ),
            viewModel.uiState.value
        )

        assertEquals(
            "40 results",
            viewModel.totalNumberOfResults.value
        )
    }
}
