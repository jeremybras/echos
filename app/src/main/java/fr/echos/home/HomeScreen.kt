package fr.echos.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import fr.echos.articles.presentation.ArticlesViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onArticleSelected: (String) -> Unit,
) {

    val viewModel: ArticlesViewModel = hiltViewModel()
    val query by viewModel.queryUiState.collectAsState()
    val domains by viewModel.domainsUiState.collectAsState()

    var shouldShowBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

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
                query = query,
                onQuery = viewModel::onQueryChange,
            )
        }
    }
}
