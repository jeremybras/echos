package fr.echos.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import fr.echos.articles.presentation.ArticleDisplayModel
import fr.echos.articles.presentation.DomainDisplayMode
import fr.echos.articles.presentation.DomainDisplayModel
import fr.echos.ui.DarkOrLightPreview

@Composable
fun ArticleHorizontalModeComposable(
    article: ArticleDisplayModel,
    onArticleSelected: (String, String) -> Unit,
) {

    val imageRatio = 0.45f
    val textRatio = 1 - imageRatio

    Column(
        modifier = Modifier
            .clickable {
                onArticleSelected(
                    article.encodedUrl,
                    article.title
                )
            },
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 4.dp),
            text = article.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 4.dp),
            text = article.dateAndAuthor,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.primary,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        ) {
            Image(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(imageRatio),
                url = article.imageUrl,
                domain = article.domain.name,
            )

            Text(
                modifier = Modifier
                    .weight(textRatio)
                    .padding(end = 16.dp),
                text = article.description,
                fontSize = 15.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun Image(
    modifier: Modifier,
    url: String,
    domain: String,
) {
    Box(
        modifier = modifier,
    ) {
        GlideImage(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large),
            model = url,
            contentDescription = null,
        )
        Text(
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.BottomEnd),
            color = Color.White,
            text = domain,
            fontSize = 12.sp,
        )
    }
}

@DarkOrLightPreview
@Composable
private fun ArticleHorizontalModeComposablePreview() {
    val article = ArticleDisplayModel(
        title = "The Trial Over Bitcoin’s True Creator Is in Session",
        description = "Description",
        dateAndAuthor = "16 Janv 2024 by Joel Khalili",
        imageUrl = "https://www.example.com/image.jpg",
        encodedUrl = "https://www.example.com",
        domain = DomainDisplayModel(
            name = "wired.com",
            displayMode = DomainDisplayMode.HORIZONTAL,
        )
    )
    ArticleHorizontalModeComposable(
        article = article,
        onArticleSelected = { _, _ -> }
    )
}
