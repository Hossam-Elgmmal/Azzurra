package com.iti.azzurra.features.settings.componnents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.iti.azzurra.R
import com.iti.azzurra.data.settings.models.TemperatureUnit
import com.iti.azzurra.features.settings.SettingsAction
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect


@Composable
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
fun TemperatureCard(
    hazeState: HazeState,
    selectedUnit: TemperatureUnit,
    onAction: (SettingsAction) -> Unit
) {
    val context = LocalContext.current.applicationContext
    Column(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .defaultMinSize(minHeight = 54.dp)
            .fillMaxWidth()
            .hazeEffect(state = hazeState)
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.4f))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.set_temperature_unit),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
        )
        ButtonGroup(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .fillMaxWidth(),
            overflowIndicator = {}
        ) {
            TemperatureUnit.entries.forEach { tempUnit ->
                toggleableItem(
                    weight = 1f,
                    checked = selectedUnit == tempUnit,
                    onCheckedChange = {
                        if (it) {
                            onAction(SettingsAction.UpdateTemperatureUnit(tempUnit))
                        }
                    },
                    label = context.getString(tempUnit.getUnitId()),
                )
            }
        }
    }
}