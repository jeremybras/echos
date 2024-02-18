package fr.echos.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.echos.articles.presentation.ArticleDisplayModel
import fr.echos.articles.presentation.DomainDisplayMode
import fr.echos.articles.presentation.DomainDisplayModel
import fr.echos.ui.DarkOrLightPreview

@Composable
internal fun ArticlesListComposable(
    modifier: Modifier,
    articles: List<ArticleDisplayModel>,
    isLoadingNextPage: Boolean,
    hasNoMorePages: Boolean,
    onArticleSelected: (String, String) -> Unit,
    onLoadMore: () -> Unit,
) {
    val listState = rememberLazyListState()
    LaunchedEffect(listState.canScrollForward) {
        if (listState.canScrollForward.not() && hasNoMorePages.not()) {
            onLoadMore()
        }
    }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        state = listState,
    ) {
        articles.forEach { article ->
            item {
                ArticleComposable(
                    article = article,
                    onArticleSelected = onArticleSelected,
                )
            }
        }
        if (isLoadingNextPage) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@DarkOrLightPreview
@Composable
fun ArticlesListComposablePreview() {
    ArticlesListComposable(
        modifier = Modifier,
        articles = listOf(
            ArticleDisplayModel(
                title = "The Trial Over Bitcoin’s True Creator Is in Session",
                description = "Description",
                dateAndAuthor = "16 Janv 2024 by Joel Khalili",
                imageUrl = "https://www.example.com/image.jpg",
                encodedUrl = "https://www.example.com",
                domain = DomainDisplayModel(
                    name = "wired.com",
                    displayMode = DomainDisplayMode.VERTICAL,
                )
            ),
            ArticleDisplayModel(
                title = "The Trial Over Bitcoin’s True Creator Is in Session",
                description = "Description",
                dateAndAuthor = "16 Janv 2024 by Joel Khalili",
                imageUrl = "https://www.example.com/image.jpg",
                encodedUrl = "https://www.example.com",
                domain = DomainDisplayModel(
                    name = "wired.com",
                    displayMode = DomainDisplayMode.VERTICAL,
                )
            ),
        ),
        isLoadingNextPage = true,
        hasNoMorePages = false,
        onArticleSelected = { _, _ -> },
        onLoadMore = {},
    )
}
