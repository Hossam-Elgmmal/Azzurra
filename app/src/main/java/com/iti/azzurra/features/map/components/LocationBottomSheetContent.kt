package com.iti.azzurra.features.map.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.iti.azzurra.R
import com.iti.azzurra.common.GradientIcon
import com.iti.azzurra.data.places.models.PlaceDetails

@Composable
fun LocationBottomSheetContent(
    place: PlaceDetails,
    onSetAsCurrentLocation: () -> Unit,
    onAddToFavorites: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GradientIcon(
                iconId = R.drawable.ic_location
            )
            Text(
                text = place.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Column (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onSetAsCurrentLocation,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_current_location),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(stringResource(R.string.set_current))
            }

            Button(
                onClick = onAddToFavorites,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_favorites_filled),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(stringResource(R.string.add_favorite))
            }
        }
    }
}