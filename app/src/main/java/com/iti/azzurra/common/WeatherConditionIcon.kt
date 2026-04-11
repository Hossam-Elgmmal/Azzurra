package com.iti.azzurra.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.iti.azzurra.R

@Composable
fun WeatherConditionIcon(
    iconCode: String,
    modifier: Modifier = Modifier
) {
    val composition by
    rememberLottieComposition(LottieCompositionSpec.RawRes(fromIconCode(iconCode)))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier
    )
}

fun fromIconCode(iconCode: String): Int = when (iconCode) {
    "01d" -> R.raw.clear_day
    "01n" -> R.raw.clear_night
    "02d" -> R.raw.partly_cloudy_day
    "02n" -> R.raw.partly_cloudy_night
    "03d" -> R.raw.cloudy
    "03n" -> R.raw.cloudy
    "04d" -> R.raw.overcast_day
    "04n" -> R.raw.overcast_night
    "09d" -> R.raw.mostly_clear_day_rain_shower
    "09n" -> R.raw.mostly_clear_night_rain_shower
    "10d" -> R.raw.extreme_day_rain
    "10n" -> R.raw.extreme_night_rain
    "11d" -> R.raw.thunderstorms_extreme_day
    "11n" -> R.raw.thunderstorms_extreme_night
    "13d" -> R.raw.extreme_day_snow
    "13n" -> R.raw.extreme_night_snow
    "50d" -> R.raw.overcast_day_fog
    "50n" -> R.raw.overcast_night_fog
    else -> R.raw.clear_day
}