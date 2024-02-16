package fr.echos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import fr.echos.articles.presentation.ArticlesViewModel
import fr.echos.ui.theme.EchosTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: ArticlesViewModel = hiltViewModel()

            EchosTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "BforBeer") }
                        )
                    }
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .padding(paddingValues = paddingValues)
                            .fillMaxSize(),
                    ) {

                    }
                }
            }
        }
    }
}