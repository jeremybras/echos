package fr.echos.home

import androidx.compose.runtime.Composable
import fr.echos.articles.presentation.ArticleDisplayModel
import fr.echos.articles.presentation.DomainDisplayMode

@Composable
internal fun ArticleComposable(
    article: ArticleDisplayModel,
    onArticleSelected: (String, String) -> Unit,
) {
    when (article.domain.displayMode) {
        DomainDisplayMode.VERTICAL -> ArticleVerticalModeComposable(
            article = article,
            onArticleSelected = onArticleSelected
        )

        DomainDisplayMode.HORIZONTAL -> ArticleHorizontalModeComposable(
            article = article,
            onArticleSelected = onArticleSelected
        )
    }
}
