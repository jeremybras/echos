package fr.echos.articles.presentation

sealed interface ArticlesUiState {

    data object Loading : ArticlesUiState

    data class Error(
        val message: String,
    ) : ArticlesUiState

    data class Success(
        val articles: List<ArticleDisplayModel>,
    ) : ArticlesUiState
}

data class ArticleDisplayModel(
    val title: String,
    val description: String,
    val imageUrl: String,
    val domain: DomainDisplayModel,
    val encodedUrl: String,
    val dateAndAuthor: String,
)
