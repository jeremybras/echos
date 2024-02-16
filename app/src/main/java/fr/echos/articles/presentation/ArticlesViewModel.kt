package fr.echos.articles.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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


    private val _query by lazy { MutableStateFlow("") }
    internal val queryUiState: StateFlow<String> by lazy { _query }

    private val _domains by lazy { MutableStateFlow(fullDomainList) }
    internal val domainsUiState: StateFlow<List<DomainUiData>> by lazy { _domains }

    init {
        viewModelScope.launch(context = dispatcher) {
            interactor.loadArticles(
                page = page,
                perPage = PAGE_SIZE,
                query = _query.value,
                domains = buildDomains(),
            )
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
