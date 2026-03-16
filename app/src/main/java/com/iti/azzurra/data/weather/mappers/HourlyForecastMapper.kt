package com.iti.azzurra.data.weather.mappers

import android.content.Context
import com.iti.azzurra.R
import com.iti.azzurra.data.settings.models.UserSettings
import com.iti.azzurra.data.weather.local.models.current_location.HourlyForecastEntity
import com.iti.azzurra.data.weather.local.models.current_location.HourlyForecastUi
import com.iti.azzurra.data.weather.remote.models.hourly.HourlyForecastResponseDto
import com.iti.azzurra.data.weather.remote.models.hourly.HourlyItemDto
import kotlin.math.roundToInt

fun HourlyForecastResponseDto.toHourlyForecastEntities(
    locationId: String
): List<HourlyForecastEntity> {
    val timezoneOffset = city?.timezoneOffset ?: 0

    return hourlyItems
        .orEmpty()
        .map { it.toHourlyForecastEntity(locationId, timezoneOffset) }
}

private fun HourlyItemDto.toHourlyForecastEntity(locationId: String, timezoneOffset: Int): HourlyForecastEntity {
    val primaryCondition = weatherConditions?.firstOrNull()

    return HourlyForecastEntity(
        timestamp = timestamp ?: 0L,
        locationId = locationId,
        temperature = temperatures?.temperature ?: 0.0,
        feelsLikeTemperature = temperatures?.feelsLikeTemperature ?: 0.0,
        minimumTemperature = temperatures?.minimumTemperature ?: 0.0,
        maximumTemperature = temperatures?.maximumTemperature ?: 0.0,
        atmosphericPressure = temperatures?.atmosphericPressure ?: 0,
        seaLevelPressure = temperatures?.seaLevelPressure ?: 0,
        groundLevelPressure = temperatures?.groundLevelPressure ?: 0,
        humidityPercentage = temperatures?.humidityPercentage ?: 0,
        windSpeed = wind?.windSpeed ?: 0.0,
        windDirectionDegrees = wind?.windDirectionDegrees ?: 0,
        windGustSpeed = wind?.windGustSpeed ?: 0.0,
        cloudCoveragePercentage = clouds?.cloudCoveragePercentage ?: 0,
        visibilityMeters = visibilityMeters ?: 0,
        precipitationChance = precipitationChance ?: 0.0,
        rainLastOneHourMm = rain?.lastOneHourMm ?: 0.0,
        snowLastOneHourMm = snow?.lastOneHourMm ?: 0.0,
        conditionId = primaryCondition?.conditionId ?: 0,
        conditionGroup = primaryCondition?.conditionGroup.orEmpty(),
        conditionDescription = primaryCondition?.conditionDescription.orEmpty(),
        iconCode = primaryCondition?.iconCode.orEmpty(),
        timestampText = timestampText.orEmpty(),
        timezoneOffset = timezoneOffset
    )
}

fun List<HourlyForecastEntity>.toHourlyForecastUiList(
    context: Context,
    settings: UserSettings,
): List<HourlyForecastUi> {
    return map {
        it.toHourlyForecastUi(context, settings)
    }
}

fun HourlyForecastEntity.toHourlyForecastUi(
    context: Context,
    settings: UserSettings,
): HourlyForecastUi {

    val timeZone = resolveTimeZone(timezoneOffset)

    return HourlyForecastUi(
        locationId = locationId,
        timeText = formatTime(timestamp, timeZone),
        iconCode = iconCode,
        conditionTitle = context.getString(
            WeatherConditionResources.getResources(conditionId).titleResId
        ),
        conditionDescription = context.getString(
            WeatherConditionResources.getResources(conditionId).descriptionResId
        ),
        temperatureText = formatTemperature(temperature, context, settings.temperatureUnit),
        feelsLikeText = formatTemperature(feelsLikeTemperature, context, settings.temperatureUnit),
        minTempText = formatTemperature(minimumTemperature, context, settings.temperatureUnit),
        maxTempText = formatTemperature(maximumTemperature, context, settings.temperatureUnit),
        humidityText = context.getString(R.string.unit_percentage, humidityPercentage),
        pressureText = context.getString(R.string.unit_pressure, atmosphericPressure),
        seaLevelPressureText = context.getString(R.string.unit_pressure, seaLevelPressure),
        groundLevelPressureText = context.getString(R.string.unit_pressure, groundLevelPressure),
        visibilityText = context.getString(R.string.unit_kilometer, visibilityMeters / 1000.0),
        cloudCoverText = context.getString(R.string.unit_percentage, cloudCoveragePercentage),
        windSpeedText = formatWindSpeed(windSpeed, context, settings.windSpeedUnit),
        windDirectionText = formatWindDirection(windDirectionDegrees, context),
        windGustText = formatWindSpeed(windGustSpeed, context, settings.windSpeedUnit),
        precipitationChanceText = context.getString(
            R.string.unit_percentage,
            (precipitationChance * 100).roundToInt(),
        ),
        rainVolumeText = if (rainLastOneHourMm > 0.0)
            context.getString(R.string.unit_millimeters, rainLastOneHourMm)
        else
            context.getString(R.string.label_none),
        snowVolumeText = if (snowLastOneHourMm > 0.0)
            context.getString(R.string.unit_millimeters, snowLastOneHourMm)
        else
            context.getString(R.string.label_none),
    )
}
