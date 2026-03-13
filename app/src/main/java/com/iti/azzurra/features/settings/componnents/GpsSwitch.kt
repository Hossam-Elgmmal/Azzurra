package com.iti.azzurra.features.settings.componnents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.iti.azzurra.R
import com.iti.azzurra.data.settings.models.LocationSource
import com.iti.azzurra.features.settings.SettingsAction
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect


@Composable
fun GpsSwitch(
    hazeState: HazeState,
    selectedLocationSource: LocationSource,
    onAction: (SettingsAction) -> Unit
) {
    Row(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .defaultMinSize(minHeight = 54.dp)
            .fillMaxWidth()
            .hazeEffect(state = hazeState)
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.use_gps),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = selectedLocationSource == LocationSource.GPS,
            onCheckedChange = {
                if (it) {
                    onAction(SettingsAction.UpdateLocationSource(LocationSource.GPS))
                } else {
                    onAction(SettingsAction.UpdateLocationSource(LocationSource.MAP))
                }
            }
        )
    }
}