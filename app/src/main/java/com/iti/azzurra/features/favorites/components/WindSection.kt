package com.iti.azzurra.features.favorites.components

import androidx.compose.foundation.layout.Arrangement
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
fun WindSection(
    forecast: DailyForecast,
) {
    CardSection {

        SectionLabel(
            text = stringResource(R.string.wind),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {

            WindStat(
                label = stringResource(R.string.speed),
                value = forecast.windSpeedText,
            )

            WindStat(
                label = stringResource(R.string.gust),
                value = forecast.windGustText,
            )

            WindStat(
                label = stringResource(R.string.direction),
                value = forecast.windDirectionText,
            )
        }
    }
}