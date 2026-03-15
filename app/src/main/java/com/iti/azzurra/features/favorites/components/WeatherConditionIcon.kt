package com.iti.azzurra.features.favorites.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.iti.azzurra.utils.Constants.ERROR_TAG

@Composable
fun WeatherConditionIcon(
    iconCode: String,
    size: Dp,
) {
    AsyncImage(
        model = "https://openweathermap.org/img/wn/$iconCode@2x.png",
        contentDescription = null,
        modifier = Modifier
            .size(size)
            .border(1.dp, MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.medium),
        contentScale = ContentScale.Fit,
        onError = {
            Log.e(ERROR_TAG, "Error loading image: ${it.result.throwable}")
        }
    )
}