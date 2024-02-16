package fr.echos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import fr.echos.detail.ArticleScreen
import fr.echos.home.HomeScreen
import fr.echos.ui.theme.EchosTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            EchosTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "home",
                ) {
                    composable("home") {
                        HomeScreen(
                            onArticleSelected = { articleUrl ->
                                // navController.navigate("articleDetail/$articleUrl")
                            },
                        )
                    }
                    composable("articleDetail") {
                        ArticleScreen(
                            articleUrl = it.arguments?.getString("articleUrl") ?: "",
                        )
                    }
                }
            }
        }
    }
}
