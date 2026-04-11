package com.iti.azzurra.features.favorites.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.iti.azzurra.R
import com.iti.azzurra.common.GradientIcon
import com.iti.azzurra.data.settings.models.LanguageSetting
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteLocationEntity
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect


@Composable
fun LocationCard(
    openWeather: () -> Unit,
    deleteLocation: () -> Unit,
    hazeState: HazeState,
    language: LanguageSetting,
    favoriteLocationEntity: FavoriteLocationEntity,
) {
    Row(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable {
                openWeather()
            }
            .defaultMinSize(minHeight = 54.dp)
            .fillMaxWidth()
            .hazeEffect(state = hazeState)
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        GradientIcon(
            iconId = R.drawable.ic_location,
            modifier = Modifier
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = if (language == LanguageSetting.ENGLISH) favoriteLocationEntity.cityNameEn
                else favoriteLocationEntity.cityNameAr,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )
            Text(
                text = "%.4f".format(favoriteLocationEntity.longitude),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
            )
            Text(
                text = "%.4f".format(favoriteLocationEntity.latitude),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
            )
        }
        IconButton(
            onClick = deleteLocation,
            modifier = Modifier,
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.colorScheme.onError,
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete),
                contentDescription = null,
            )
        }
    }
}