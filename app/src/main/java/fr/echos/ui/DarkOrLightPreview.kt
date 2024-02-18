package fr.echos.ui

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Dark Mode",
    group = "light or dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)

@Preview(
    name = "Light Mode",
    group = "light or dark",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)

annotation class DarkOrLightPreview