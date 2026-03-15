package com.iti.azzurra.data.weather.mappers

import android.content.Context
import com.iti.azzurra.R
import com.iti.azzurra.data.settings.models.UserSettings
import com.iti.azzurra.data.weather.local.models.favorites.DailyForecast
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteDailyForecastEntity
import kotlin.math.roundToInt

fun FavoriteDailyForecastEntity.toDailyForecast(
    context: Context,
    settings: UserSettings,
): DailyForecast {

    val timeZone = resolveTimeZone(timezoneOffset)

    return DailyForecast(
        locationId = locationId,
        iconCode = iconCode,
        dateText = formatDate(dayTimestamp, timeZone),
        cityCountryText = "$cityName, $countryCode",
        sunriseText = formatTime(sunriseTimestamp, timeZone),
        sunsetText = formatTime(sunsetTimestamp, timeZone),
        dayTempText = formatTemperature(dayTemperature, context, settings.temperatureUnit),
        nightTempText = formatTemperature(nightTemperature, context, settings.temperatureUnit),
        morningTempText = formatTemperature(morningTemperature, context, settings.temperatureUnit),
        eveningTempText = formatTemperature(eveningTemperature, context, settings.temperatureUnit),
        minTempText = formatTemperature(minimumTemperature, context, settings.temperatureUnit),
        maxTempText = formatTemperature(maximumTemperature, context, settings.temperatureUnit),
        dayFeelsLikeText = formatTemperature(dayFeelsLike, context, settings.temperatureUnit),
        nightFeelsLikeText = formatTemperature(nightFeelsLike, context, settings.temperatureUnit),
        morningFeelsLikeText = formatTemperature(
            morningFeelsLike,
            context,
            settings.temperatureUnit
        ),
        eveningFeelsLikeText = formatTemperature(
            eveningFeelsLike,
            context,
            settings.temperatureUnit
        ),
        pressureText = context.getString(R.string.unit_pressure, atmosphericPressure),
        humidityText = context.getString(R.string.unit_percentage, humidityPercentage),
        cloudCoverText = context.getString(R.string.unit_percentage, cloudCoveragePercentage),
        windSpeedText = formatWindSpeed(windSpeed, context, settings.windSpeedUnit),
        windGustText = formatWindSpeed(windGustSpeed, context, settings.windSpeedUnit),
        windDirectionText = formatWindDirection(windDirectionDegrees, context),
        precipitationProbabilityText = context.getString(
            R.string.unit_percentage,
            (precipitationProbability * 100).roundToInt(),
        ),
        rainVolumeText = if (rainVolumeMm > 0.0)
            context.getString(R.string.unit_millimeters, rainVolumeMm)
        else
            context.getString(R.string.label_none),
        snowVolumeText = if (snowVolumeMm > 0.0)
            context.getString(R.string.unit_millimeters, snowVolumeMm)
        else
            context.getString(R.string.label_none),
        conditionTitle = context.getString(
            WeatherConditionResources.getResources(conditionId).titleResId
        ),
        conditionDescription = context.getString(
            WeatherConditionResources.getResources(conditionId).descriptionResId
        ),
    )
}
