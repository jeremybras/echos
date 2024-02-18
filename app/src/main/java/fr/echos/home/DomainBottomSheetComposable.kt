package fr.echos.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fr.echos.articles.presentation.DomainDisplayModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DomainBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    domains: List<DomainDisplayModel>,
    onDomainSelected: (DomainDisplayModel) -> Unit,
    bottomPadding: Dp,
) {
    ModalBottomSheet(
        windowInsets = WindowInsets.displayCutout,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = bottomPadding)
                    .verticalScroll(rememberScrollState()),
            ) {
                domains.forEach { domain ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        onClick = {
                            onDomainSelected(domain)
                        },
                        colors = CardDefaults.cardColors(
                            containerColor = if (domain.isSelected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.surface
                            },
                        ),
                        shape = MaterialTheme.shapes.medium,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(
                                        horizontal = 16.dp,
                                        vertical = 8.dp,
                                    ),
                                text = domain.name,
                            )
                            if (domain.isSelected) {
                                Icon(
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp),
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        },
    )
}
