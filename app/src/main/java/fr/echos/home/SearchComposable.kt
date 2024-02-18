package fr.echos.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.echos.R
import fr.echos.ui.DarkOrLightPreview

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun Search(
    modifier: Modifier,
    resultNumber: String,
    query: String,
    onQuery: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier,
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = query,
            onValueChange = { onQuery(it) },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_placeholder),
                )
            },
            maxLines = 1,
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(
                onSearch = { keyboardController?.hide() }
            ),
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp),
            textAlign = TextAlign.End,
            text = resultNumber,
            fontSize = 12.sp,
        )
    }
}

@DarkOrLightPreview
@Composable
fun SearchPreview() {
    Search(
        modifier = Modifier,
        resultNumber = "20 results",
        query = "google",
        onQuery = {},
    )
}
