package com.iti.azzurra.features.settings.componnents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.iti.azzurra.R
import com.iti.azzurra.features.settings.SettingsAction
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect


@Composable
fun OpenDialogSettingsCard(
    onAction: () -> Unit,
    hazeState: HazeState,
    iconId: Int,
    titleId: Int,
    valueId: Int
) {
    Row(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable {
                onAction()
            }
            .defaultMinSize(minHeight = 48.dp)
            .fillMaxWidth()
            .hazeEffect(state = hazeState)
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.4f))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = stringResource(titleId),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(valueId),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
        )
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}