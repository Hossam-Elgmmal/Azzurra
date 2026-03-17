package com.iti.azzurra.features.alerts.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
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
import com.iti.azzurra.common.GradientIcon
import com.iti.azzurra.data.alert.local.models.AlertEntity
import com.iti.azzurra.data.alert.local.models.AlertRequirement
import com.iti.azzurra.data.alert.local.models.AlertType
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun WeatherAlertCard(
    alert: AlertEntity,
    onItemDelete: () -> Unit,
    hazeState: HazeState,
    onToggleActive: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .fillMaxWidth()
            .hazeEffect(hazeState)
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.6f))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            AlertIcon(alert)
            Spacer(Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(
                        when (alert.alertRequirement) {
                            AlertRequirement.TEMPERATURE -> R.string.temperature
                            AlertRequirement.SNOW -> R.string.snow
                            AlertRequirement.RAIN -> R.string.rain
                        }
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )

                if (alert.alertRequirement == AlertRequirement.TEMPERATURE) {
                    Text(
                        text = "${alert.minTemperature} - ${alert.maxTemperature}",
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            }
            Spacer(Modifier.width(8.dp))
            IconButton(onClick = onItemDelete) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_delete),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AlertTimeRow(
                label = stringResource(R.string.start),
                timestamp = alert.startTime
            )
            AlertTimeRow(
                label = stringResource(R.string.end),
                timestamp = alert.endTime
            )
            Row {
                Text(
                    text = stringResource(R.string.duration),
                )
                Text(
                    text = formatDuration(Duration.ofMillis(alert.endTime - alert.startTime)),
                )
            }
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(
                    if (alert.isActive) R.string.active else R.string.inactive
                ),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color =
                    if (alert.isActive)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.outline
            )
            Spacer(Modifier.weight(1f))
            Switch(
                checked = alert.isActive,
                onCheckedChange = { newValue ->
                    onToggleActive(newValue)
                }
            )
        }
    }
}

@Composable
private fun AlertIcon(
    alert: AlertEntity
) {
    Box(
        modifier = Modifier
            .size(42.dp)
            .background(
                MaterialTheme.colorScheme.tertiaryContainer,
                RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        GradientIcon(
            iconId =
                if (alert.alarmType == AlertType.NOTIFICATION)
                    R.drawable.ic_notification
                else
                    R.drawable.ic_alarm,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun AlertTimeRow(
    label: String,
    timestamp: Long,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = formatTimestamp(timestamp),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

private fun formatTimestamp(millis: Long): String {
    return formatTime(
        LocalDateTime.ofInstant(
            Instant.ofEpochMilli(millis),
            ZoneId.systemDefault()
        )
    )
}
