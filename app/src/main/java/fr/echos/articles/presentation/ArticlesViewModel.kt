package fr.echos.articles.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.echos.articles.domain.ArticleResult
import fr.echos.articles.domain.ArticlesInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    @Named("ioDispatcher") private val dispatcher: CoroutineDispatcher,
    private val interactor: ArticlesInteractor,
    private val articleTransformer: ArticleTransformer,
) : ViewModel() {

    companion object {

        private val fullDomainList = listOf(
            DomainUiData("bbc.co.uk"),
            DomainUiData("techcrunch.com"),
            DomainUiData("engadget.com"),
            DomainUiData("mashable.com"),
            DomainUiData("thenextweb.com"),
            DomainUiData("wired.com"),
            DomainUiData("arstechnica.com"),
            DomainUiData("techradar.com"),
            DomainUiData("theverge.com"),
            DomainUiData("recode.net"),
            DomainUiData("venturebeat.com"),
            DomainUiData("cnet.com"),
            DomainUiData("gizmodo.com"),
            DomainUiData("slashdot.org"),
            DomainUiData("lifehacker.com"),
            DomainUiData("gigaom.com"),
        )

        private const val PAGE_SIZE = 10
        private var page = 1
    }


    private val _query = MutableStateFlow("")
    internal val queryUiState: StateFlow<String> = _query

    private val _domains = MutableStateFlow(fullDomainList)
    internal val domainsUiState: StateFlow<List<DomainUiData>> = _domains

    private val _uiState = MutableStateFlow<ArticlesUiState>(ArticlesUiState.Loading)
    internal val uiState: StateFlow<ArticlesUiState> = _uiState

    init {
        viewModelScope.launch(context = dispatcher) {
            loadArticles()
        }
    }

    private fun loadArticles() {

        _uiState.value = ArticlesUiState.Loading

        val result = interactor.loadArticles(
            page = page,
            perPage = PAGE_SIZE,
            query = _query.value.takeIf { it.isNotBlank() },
            domains = buildDomains(),
        )
        _uiState.value = when (result) {
            is ArticleResult.Error -> ArticlesUiState.Error(
                message = result.message ?: "An error occurred",
            )

            is ArticleResult.Success -> {
                val articles = articleTransformer.transform(result.articles)
                articles.forEach { article ->
                    println(article)
                }
                ArticlesUiState.Success(
                    articles = articles,
                )
            }
        }
    }

    fun onDomainSelected(domain: DomainUiData) {
        _domains.value = _domains.value.map {
            if (it.name == domain.name) {
                it.copy(isSelected = !it.isSelected)
            } else {
                it
            }
        }
    }

    private fun buildDomains(): List<String> {
        return _domains.value
            .filter {
                it.isSelected
            }.map {
                it.name
            }
    }

    fun onQueryChange(query: String) {
        _query.value = query
    }
}
