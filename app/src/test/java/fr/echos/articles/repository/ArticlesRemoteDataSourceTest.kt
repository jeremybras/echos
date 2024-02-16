package fr.echos.articles.repository

import fr.echos.articles.domain.Article
import fr.echos.articles.domain.ArticleException
import fr.echos.testutils.enqueue
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExtendWith(MockitoExtension::class)
class ArticlesRemoteDataSourceTest {

    private lateinit var server: MockWebServer

    private lateinit var service: EchosService

    @Mock
    private lateinit var errorParser: ErrorParser

    @Mock
    private lateinit var articleResponseConverter: ArticleResponseConverter

    private lateinit var dataSource: ArticlesRemoteDataSource

    @BeforeEach
    fun setUp() {
        server = MockWebServer()
        service = Retrofit
            .Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EchosService::class.java)
        dataSource = ArticlesRemoteDataSource(
            service = service,
            errorParser = errorParser,
            articleResponseConverter = articleResponseConverter,
        )
    }

    @AfterEach
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `loadRequest - given a successful request - then should return success`() {
        // Given
        server.enqueue("articles_success.json")
        val expectedResponse = ArticleResponse.Success(
            totalResults = 2031,
            articles = listOf(
                ArticleJson(
                    source = ArticleSourceJson(
                        id = "engadget",
                        name = "Engadget",
                    ),
                    author = "Sarah Fielding",
                    title = "NVIDIA becomes the third most valuable US company at Alphabet's expense",
                    description = "NVIDIA is doing very well for itself. The chip maker has overtaken Alphabet, Google's parent company, to become the third most valuable company in the United States, Reuters reports. The news comes almost immediately after NVIDIA pushed past Amazon in the ran…",
                    url = "https://www.engadget.com/nvidia-becomes-the-third-most-valuable-us-company-at-alphabets-expense-123503967.html",
                    urlToImage = "https://s.yimg.com/ny/api/res/1.2/_G6EQiTAEfMwaYoTEQ2TyA--/YXBwaWQ9aGlnaGxhbmRlcjt3PTEyMDA7aD03OTk-/https://s.yimg.com/os/creatr-uploaded-images/2023-12/7de70ac0-ae59-11ee-b5ff-5d788b9b7667",
                    publishedAt = "2024-02-15T12:35:03Z",
                    content = "NVIDIA is doing very well for itself. The chip maker has overtaken Alphabet, Google's parent company, to become the third most valuable company in the United States, Reuters reports. The news comes a… [+1290 chars]",
                )
            )
        )
        val article = mock(Article::class.java)
        given(articleResponseConverter.convert(expectedResponse)).willReturn(listOf(article))

        // When
        val result = dataSource.loadRequest(
            page = 1,
            perPage = 20,
            query = null,
            domains = listOf("engadget.com", "techcrunch.com"),
        )

        // Then
        then(articleResponseConverter).should().convert(expectedResponse)
        assertEquals(
            listOf(article),
            result
        )
    }

    @Test
    fun `loadRequest - given a request with an error - then should throw an exception`() {
        // Given
        server.enqueue(
            MockResponse()
                .setResponseCode(400)
                .setBody("Client Error")
        )
        val expectedResponse = ArticleResponse.Error(
            code = "apiKeyInvalid",
            message = "Your API key is invalid or incorrect. Check your key, or go to https://newsapi.org to create a free API key."
        )
        given(errorParser.parseError(any())).willReturn(expectedResponse)

        // When / Then
        assertThrows(ArticleException::class.java) {
            dataSource.loadRequest(
                page = 1,
                perPage = 20,
                query = null,
                domains = emptyList(),
            )
        }
    }
}
