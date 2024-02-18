package fr.echos.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fr.echos.R
import fr.echos.ui.DarkOrLightPreview

@Composable
internal fun ArticlesErrorComposable(
    modifier: Modifier,
    message: String,
    onRetry: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = message,
            textAlign = TextAlign.Center,
        )

        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = onRetry,
            content = {
                Text(
                    text = stringResource(id = R.string.retry_button),
                )
            }
        )
    }
}

@DarkOrLightPreview
@Composable
fun ArticlesErrorComposablePreview() {
    ArticlesErrorComposable(
        modifier = Modifier,
        message = "An error occurred",
        onRetry = {},
    )
}
