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
            "bbc.co.uk",
            "techcrunch.com",
            "engadget.com",
            "mashable.com",
            "thenextweb.com",
            "wired.com",
            "arstechnica.com",
            "techradar.com",
            "theverge.com",
            "recode.net",
            "techcrunch.com",
            "venturebeat.com",
            "cnet.com",
            "gizmodo.com",
            "slashdot.org",
            "lifehacker.com",
            "gigaom.com",
        )

        private const val PAGE_SIZE = 10
        private var page = 1
        private var query = ""
        private val domains = mutableListOf<String>()
    }


    private val _query by lazy { MutableStateFlow("") }
    internal val query: StateFlow<String> by lazy { _query }

    init {
        viewModelScope.launch(context = dispatcher) {
            interactor.loadArticles(
                page = page,
                perPage = PAGE_SIZE,
                query = query.value,
                domains = domains,
            )
        }
    }

    fun onQueryChange(query: String) {
        _query.value = query
    }
}
