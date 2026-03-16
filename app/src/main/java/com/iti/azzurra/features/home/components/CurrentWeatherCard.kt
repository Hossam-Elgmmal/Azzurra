package com.iti.azzurra.features.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.iti.azzurra.R
import com.iti.azzurra.common.GradientIcon
import com.iti.azzurra.common.WeatherConditionIcon
import com.iti.azzurra.data.weather.local.models.current_location.CurrentWeatherUi
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect

@Composable
fun CurrentWeatherCard(
    cityName: String,
    weather: CurrentWeatherUi,
    hazeState: HazeState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .fillMaxWidth()
            .hazeEffect(state = hazeState)
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            WeatherConditionIcon(
                iconCode = weather.iconCode,
                size = 56.dp,
            )
            Column(

            ) {
                Text(
                    text = cityName,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Text(
                    text = weather.timeText,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }


        Text(
            text = weather.temperatureText,
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = stringResource(R.string.feels_like, weather.feelsLikeText),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = weather.conditionTitle,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = weather.conditionDescription,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )

            }
            Spacer(modifier = Modifier.weight(1f))
            Column (
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = stringResource(R.string.max, weather.maxTempText),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = stringResource(R.string.min, weather.minTempText),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }


        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            WeatherStatItem(
                iconRes = R.drawable.ic_humidity,
                label = stringResource(R.string.humidity),
                value = weather.humidityText,
                modifier = Modifier.weight(1f)
            )
            WeatherStatItem(
                iconRes = R.drawable.ic_wind,
                label = stringResource(R.string.wind),
                value = weather.windSpeedText,
                modifier = Modifier.weight(1f)
            )
            WeatherStatItem(
                iconRes = R.drawable.ic_pressure,
                label = stringResource(R.string.pressure),
                value = weather.pressureText,
                modifier = Modifier.weight(1f)
            )
            WeatherStatItem(
                iconRes = R.drawable.ic_visibility,
                label = stringResource(R.string.visibility),
                value = weather.visibilityText,
                modifier = Modifier.weight(1f)
            )
        }

        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            WeatherStatItem(
                iconRes = R.drawable.ic_sunrise,
                label = stringResource(R.string.rise),
                value = weather.sunriseText,
                modifier = Modifier.weight(1f)
            )
            WeatherStatItem(
                iconRes = R.drawable.ic_sunset,
                label = stringResource(R.string.set),
                value = weather.sunsetText,
                modifier = Modifier.weight(1f)
            )
            WeatherStatItem(
                iconRes = R.drawable.ic_cloud,
                label = stringResource(R.string.clouds),
                value = weather.cloudCoverText,
                modifier = Modifier.weight(1f)
            )
            WeatherStatItem(
                iconRes = R.drawable.ic_wind_direction,
                label = stringResource(R.string.direction),
                value = weather.windDirectionText,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun WeatherStatItem(
    iconRes: Int,
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        GradientIcon(
            iconId = iconRes,
            modifier = Modifier.size(20.dp),
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}