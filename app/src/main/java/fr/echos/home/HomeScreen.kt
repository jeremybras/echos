package fr.echos.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fr.echos.articles.presentation.ArticlesUiState
import fr.echos.articles.presentation.ArticlesViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onArticleSelected: (String, String) -> Unit,
) {

    val viewModel: ArticlesViewModel = hiltViewModel()
    val query by viewModel.queryUiState.collectAsState()
    val domains by viewModel.domainsUiState.collectAsState()
    val totalNumberOfResults by viewModel.totalNumberOfResults.collectAsState()
    val articlesUiState by viewModel.uiState.collectAsState()
    val isLoadingNextPage by viewModel.isLoadingNextPage.collectAsState()
    val hasNoMorePages by viewModel.hasNoMorePages.collectAsState()

    var shouldShowBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Echos",
                    )
                },
                actions = {
                    TextButton(
                        onClick = {
                            shouldShowBottomSheet = true
                        },
                    ) {
                        Text(
                            text = "Domains",
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        if (shouldShowBottomSheet) {
            DomainBottomSheet(
                bottomPadding = bottomPadding,
                onDismissRequest = { shouldShowBottomSheet = false },
                sheetState = sheetState,
                domains = domains,
                onDomainSelected = { domain ->
                    viewModel.onDomainSelected(domain)
                },
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
        ) {

            Search(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .padding(bottom = 4.dp),
                resultNumber = totalNumberOfResults,
                query = query,
                onQuery = viewModel::onQueryChange,
            )

            when (val uiState = articlesUiState) {
                ArticlesUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is ArticlesUiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = uiState.message,
                            textAlign = TextAlign.Center,
                        )

                        Button(
                            modifier = Modifier.padding(top = 16.dp),
                            onClick = {
                                viewModel.retry()
                            },
                            content = {
                                Text(
                                    text = "Retry",
                                )
                            }
                        )
                    }
                }

                is ArticlesUiState.Success -> {

                    val listState = rememberLazyListState()
                    LaunchedEffect(listState.canScrollForward.not()) {
                        if (listState.canScrollForward.not() && hasNoMorePages.not()) {
                            viewModel.loadMore()
                        }
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(vertical = 16.dp),
                        state = listState,
                    ) {
                        uiState.articles.forEach { article ->
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
            }
        }
    }
}
