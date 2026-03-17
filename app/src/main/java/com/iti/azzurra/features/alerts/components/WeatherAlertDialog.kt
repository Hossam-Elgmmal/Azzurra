package com.iti.azzurra.features.alerts.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.iti.azzurra.R
import com.iti.azzurra.data.alert.local.models.AlertEntity
import com.iti.azzurra.data.alert.local.models.AlertRequirement
import com.iti.azzurra.data.alert.local.models.AlertType
import com.iti.azzurra.data.settings.models.TemperatureUnit
import com.iti.azzurra.features.favorites.components.SectionLabel
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun WeatherAlertDialog(
    onDismiss: () -> Unit,
    currentUnit: TemperatureUnit = TemperatureUnit.CELSIUS,
    onConfirm: (AlertEntity) -> Unit,
) {
    var selectedType by remember { mutableStateOf(AlertType.NOTIFICATION) }
    var selectedRequirement by remember { mutableStateOf(AlertRequirement.TEMPERATURE) }
    var tempRange by remember { mutableStateOf(10f..30f) }

    val allowedRange = when (currentUnit) {
        TemperatureUnit.CELSIUS -> -20f..50f
        TemperatureUnit.FAHRENHEIT -> -4f..122f
        TemperatureUnit.KELVIN -> 253f..323f
    }

    val unitSymbol = when (currentUnit) {
        TemperatureUnit.CELSIUS -> "°C"
        TemperatureUnit.FAHRENHEIT -> "°F"
        TemperatureUnit.KELVIN -> "K"
    }

    val context = LocalContext.current.applicationContext

    var startTime by remember { mutableStateOf(LocalDateTime.now()) }
    var endTime by remember { mutableStateOf(LocalDateTime.now()) }
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier.size(44.dp)
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back),
                                contentDescription = stringResource(R.string.go_back)
                            )
                        }
                    },
                    title = {
                        Text(
                            text = stringResource(R.string.add_weather_alert),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    )
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                item {
                    SectionLabel(text = stringResource(R.string.alert_type))
                    SingleChoiceSegmentedButtonRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
                        AlertRequirement.entries.forEachIndexed { index, requirement ->
                            SegmentedButton(
                                selected = selectedRequirement == requirement,
                                onClick = { selectedRequirement = requirement },
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = AlertRequirement.entries.size
                                ),
                                icon = {}
                            ) {
                                Text(
                                    text = stringResource(requirement.getLabelId()),
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }

                if (selectedRequirement == AlertRequirement.TEMPERATURE) {
                    item {
                        SectionLabel(text = stringResource(R.string.temperature_range))
                        Text(
                            text = "${tempRange.start.roundToInt()}$unitSymbol  :  ${tempRange.endInclusive.roundToInt()}$unitSymbol",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        RangeSlider(
                            value = tempRange,
                            onValueChange = { tempRange = it },
                            valueRange = allowedRange,
                            steps = 0,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                item {
                    SectionLabel(text = stringResource(R.string.alert_type))
                    ButtonGroup(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        overflowIndicator = {}
                    ) {
                        AlertType.entries.forEach { type ->
                            toggleableItem(
                                weight = 1f,
                                checked = selectedType == type,
                                onCheckedChange = { if (it) selectedType = type },
                                label = context.getString(type.getLabelId())
                            )
                        }
                    }
                }
                item {
                    AlertTimePickerSection(
                        startTime = startTime,
                        endTime = endTime,
                        onStartChange = {
                            startTime = it
                        },
                        onEndChange = {
                            endTime = it
                        }
                    )
                }

                item {
                    Button(
                        onClick = {
                            onConfirm(
                                AlertEntity(
                                    startTime = startTime.atZone(ZoneId.systemDefault()).toInstant()
                                        .toEpochMilli(),
                                    endTime = endTime.atZone(ZoneId.systemDefault()).toInstant()
                                        .toEpochMilli(),
                                    alarmType = selectedType,
                                    isActive = true,
                                    minTemperature = tempRange.start.toDouble(),
                                    maxTemperature = tempRange.endInclusive.toDouble(),
                                    alertRequirement = selectedRequirement
                                )
                            )
                            onDismiss()
                        },
                        enabled = startTime.isBefore(endTime),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text(text = stringResource(R.string.save_alert))
                    }
                }
            }
        }
    }
}