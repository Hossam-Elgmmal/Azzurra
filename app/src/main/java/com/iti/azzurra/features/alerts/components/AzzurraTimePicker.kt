package com.iti.azzurra.features.alerts.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.iti.azzurra.R
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertTimePickerSection(
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    onStartChange: (LocalDateTime) -> Unit,
    onEndChange: (LocalDateTime) -> Unit
) {
    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        Text(
            text = stringResource(R.string.alert_time),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedButton(
                onClick = { showStartPicker = true },
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(R.string.starts, formatTime(startTime)))
            }

            OutlinedButton(
                onClick = { showEndPicker = true },
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(R.string.ends, formatTime(endTime)))
            }
        }

        val duration = Duration.between(startTime, endTime)
        Text(
            text = if (!duration.isNegative && !duration.isZero) {
                stringResource(R.string.durations, formatDuration(duration))
            } else {
                stringResource(R.string.end_time_must_be_after_start_time)
            },
            color = if (!duration.isNegative && !duration.isZero)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.labelMedium
        )
    }

    if (showStartPicker) {
        TimePickerDialog(
            initialTime = startTime,
            onDismiss = { showStartPicker = false },
            onConfirm = {
                onStartChange(it)
                showStartPicker = false
            }
        )
    }

    if (showEndPicker) {
        TimePickerDialog(
            initialTime = endTime,
            onDismiss = { showEndPicker = false },
            onConfirm = {
                onEndChange(it)
                showEndPicker = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    initialTime: LocalDateTime,
    onDismiss: () -> Unit,
    onConfirm: (LocalDateTime) -> Unit
) {
    val state = rememberTimePickerState(
        initialHour = initialTime.hour,
        initialMinute = initialTime.minute,
        is24Hour = false
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    val pickedTime = initialTime
                        .withHour(state.hour)
                        .withMinute(state.minute)
                    onConfirm(pickedTime)
                }
            ) { Text(stringResource(R.string.ok)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.cancels)) }
        },
        text = {
            TimePicker(state = state)
        }
    )
}

fun formatTime(time: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault())
    return time.format(formatter)
}

fun formatDuration(duration: Duration): String {
    val hours = duration.toHours()
    val minutes = duration.toMinutes() % 60
    return buildString {
        if (hours > 0) append("${hours}h ")
        if (minutes > 0) append("${minutes}m")
        if (hours == 0L && minutes == 0L) append("<1m")
    }.trim()
}