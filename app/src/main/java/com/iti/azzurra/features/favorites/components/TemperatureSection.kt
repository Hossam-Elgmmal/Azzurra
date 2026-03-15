package com.iti.azzurra.features.favorites.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.iti.azzurra.R
import com.iti.azzurra.data.weather.local.models.favorites.DailyForecast

@Composable
fun TemperatureSection(
    forecast: DailyForecast,
) {
    CardSection {
        SectionLabel(
            text = stringResource(R.string.temperature),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {

            TempCell(
                label = stringResource(R.string.morning),
                temp = forecast.morningTempText,
                feelsLike = forecast.morningFeelsLikeText,
                modifier = Modifier.weight(1f),
            )

            Divider()

            TempCell(
                label = stringResource(R.string.day),
                temp = forecast.dayTempText,
                feelsLike = forecast.dayFeelsLikeText,
                modifier = Modifier.weight(1f),
            )

            Divider()

            TempCell(
                label = stringResource(R.string.evening),
                temp = forecast.eveningTempText,
                feelsLike = forecast.eveningFeelsLikeText,
                modifier = Modifier.weight(1f),
            )

            Divider()

            TempCell(
                label = stringResource(R.string.night),
                temp = forecast.nightTempText,
                feelsLike = forecast.nightFeelsLikeText,
                modifier = Modifier.weight(1f),
            )
        }
    }
}