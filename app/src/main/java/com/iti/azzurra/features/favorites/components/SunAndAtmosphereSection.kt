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
import com.iti.azzurra.data.weather.local.models.favorites.DailyForecastUi

@Composable
fun SunAndAtmosphereSection(
    forecast: DailyForecastUi,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {

        CardSection(
            modifier = Modifier.weight(1f),
        ) {

            SectionLabel(
                text = stringResource(R.string.sun),
            )

            Spacer(modifier = Modifier.height(8.dp))

            InfoRow(
                icon = R.drawable.ic_sunrise,
                label = stringResource(R.string.rise),
                value = forecast.sunriseText,
            )

            Spacer(modifier = Modifier.height(4.dp))

            InfoRow(
                icon = R.drawable.ic_sunset,
                label = stringResource(R.string.set),
                value = forecast.sunsetText,
            )
        }

        CardSection(
            modifier = Modifier.weight(1f),
        ) {

            SectionLabel(
                text = stringResource(R.string.atmosphere),
            )

            Spacer(modifier = Modifier.height(8.dp))

            InfoRow(
                icon = R.drawable.ic_humidity,
                label = stringResource(R.string.humidity),
                value = forecast.humidityText,
            )

            Spacer(modifier = Modifier.height(4.dp))

            InfoRow(
                icon = R.drawable.ic_cloud,
                label = stringResource(R.string.clouds),
                value = forecast.cloudCoverText,
            )
        }
    }
}