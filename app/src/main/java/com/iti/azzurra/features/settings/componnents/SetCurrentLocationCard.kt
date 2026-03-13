package com.iti.azzurra.features.settings.componnents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iti.azzurra.R
import com.iti.azzurra.common.GradientIcon
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SetCurrentLocationCard(
    hazeState: HazeState,
    cityName: String,
    openMap: () -> Unit,
    openGps: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .defaultMinSize(minHeight = 54.dp)
            .fillMaxWidth()
            .hazeEffect(state = hazeState)
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (cityName.isNotBlank()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                GradientIcon(
                    iconId = R.drawable.ic_location,
                    modifier = Modifier
                )
                Text(
                    text = cityName,
                    style = MaterialTheme.typography.headlineSmall,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
            HorizontalDivider()
        }
        Text(
            text = stringResource(R.string.set_current_location),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
        Row(
            modifier = Modifier
                .align(Alignment.End)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ElevatedButton(
                onClick = openMap,
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.map),
                    fontWeight = FontWeight.Bold
                )
            }
            ElevatedButton(
                onClick = openGps,
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.gps),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}