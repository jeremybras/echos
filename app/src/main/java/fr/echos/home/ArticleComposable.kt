package fr.echos.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.echos.articles.presentation.ArticleDisplayModel

@Composable
internal fun ArticleComposable(
    article: ArticleDisplayModel,
    onArticleSelected: (String) -> Unit,
) {

    Column(
        modifier = Modifier,
    ) {
        // TODO : Image + Domain
        // TODO : Date
        // TODO : Title
        // TODO : Description
        // TODO : Author
    }

}
