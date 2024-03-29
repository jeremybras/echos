package fr.echos.articles.presentation

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.echos.R
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
    private val resources: Resources,
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 20
    }

    private var page = 1
    private val articles = mutableListOf<ArticleDisplayModel>()

    private val _query = MutableStateFlow("")
    internal val queryUiState: StateFlow<String> = _query

    private val _domains = MutableStateFlow(fullDomainList)
    internal val domainsUiState: StateFlow<List<DomainDisplayModel>> = _domains

    private val _totalNumberOfResults = MutableStateFlow("")
    internal val totalNumberOfResults: StateFlow<String> = _totalNumberOfResults

    private val _uiState = MutableStateFlow<ArticlesUiState>(ArticlesUiState.Loading)
    internal val uiState: StateFlow<ArticlesUiState> = _uiState

    private val _isLoadingNextPage = MutableStateFlow(false)
    internal val isLoadingNextPage: StateFlow<Boolean> = _isLoadingNextPage

    private val _hasNoMorePages = MutableStateFlow(false)
    internal val hasNoMorePages: StateFlow<Boolean> = _hasNoMorePages

    init {
        loadArticles()
    }

    fun onDomainSelected(domain: DomainDisplayModel) {
        _domains.value = _domains.value.map {
            if (it.name == domain.name) {
                it.copy(isSelected = !it.isSelected)
            } else {
                it
            }
        }
        if (_uiState.value is ArticlesUiState.Error) {
            _uiState.value = ArticlesUiState.Loading
        }
        reset()
        loadArticles()
    }

    fun onQueryChange(query: String) {
        _query.value = query
        reset()
        loadArticles()
    }

    fun loadMore() {
        if (_isLoadingNextPage.value || _hasNoMorePages.value) return
        _isLoadingNextPage.value = true
        page++
        loadArticles()
    }

    fun retry() {
        _uiState.value = ArticlesUiState.Loading
        loadArticles()
    }

    private fun loadArticles() {
        viewModelScope.launch(context = dispatcher) {
            val result = interactor.loadArticles(
                page = page,
                perPage = PAGE_SIZE,
                query = _query.value.takeIf { it.isNotBlank() },
                domains = buildDomains(),
            )
            _uiState.value = when (result) {
                is ArticleResult.Error -> ArticlesUiState.Error(
                    message = result.message ?: resources.getString(R.string.generic_error_message),
                )

                is ArticleResult.Success -> {

                    _hasNoMorePages.value = result.articlesNumber <= page * PAGE_SIZE

                    _totalNumberOfResults.value = resources.getQuantityString(
                        R.plurals.total_number_of_results,
                        result.articlesNumber,
                        result.articlesNumber,
                    )

                    val transformedArticles = articleTransformer.transform(result.articles)
                    articles.addAll(transformedArticles)

                    ArticlesUiState.Success(
                        articles = articles,
                    )
                }
            }

            _isLoadingNextPage.value = false
        }
    }

    private fun reset() {
        page = 1
        articles.clear()
    }

    private fun buildDomains(): List<String> {
        return _domains.value
            .filter { it.isSelected }
            .map { it.name }
    }
}
