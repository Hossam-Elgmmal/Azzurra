package com.iti.azzurra.data.weather.mappers

import android.content.Context
import com.iti.azzurra.R
import com.iti.azzurra.data.settings.models.UserSettings
import com.iti.azzurra.data.weather.local.models.current_location.CurrentWeatherEntity
import com.iti.azzurra.data.weather.local.models.current_location.CurrentWeatherUi
import com.iti.azzurra.data.weather.remote.models.current.CurrentWeatherResponseDto

fun CurrentWeatherResponseDto.toCurrentWeatherEntity(
    locationId: String,
): CurrentWeatherEntity {
    val primaryCondition = weatherConditions?.firstOrNull()
    return CurrentWeatherEntity(
        locationId = locationId,
        latitude = coordinates?.latitude ?: 0.0,
        longitude = coordinates?.longitude ?: 0.0,
        cityId = cityId ?: 0,
        cityName = cityName ?: "",
        countryCode = systemMetadata?.countryCode ?: "",
        timezoneOffsetSeconds = timezoneOffsetSeconds ?: 0,
        conditionId = primaryCondition?.conditionId ?: 0,
        conditionGroup = primaryCondition?.conditionGroup ?: "",
        conditionDescription = primaryCondition?.conditionDescription ?: "",
        conditionIconCode = primaryCondition?.iconCode ?: "",
        temperatureCelsius = atmosphericReadings?.temperature ?: 0.0,
        feelsLikeTemperature = atmosphericReadings?.feelsLikeTemperature ?: 0.0,
        minimumTemperature = atmosphericReadings?.minimumTemperature ?: 0.0,
        maximumTemperature = atmosphericReadings?.maximumTemperature ?: 0.0,
        atmosphericPressure = atmosphericReadings?.atmosphericPressure ?: 0,
        humidityPercent = atmosphericReadings?.humidityPercentage ?: 0,
        windSpeedMetersPerSecond = windConditions?.windSpeed ?: 0.0,
        windDirectionDegrees = windConditions?.windDirectionDegrees ?: 0,
        windGustMetersPerSecond = windConditions?.windGustSpeed ?: 0.0,
        rainLastHourMillimeters = rainAccumulation?.lastHourMillimeters,
        snowLastHourMillimeters = snowAccumulation?.lastHourMillimeters,
        cloudCoveragePercent = cloudCoverage?.cloudCoveragePercentage ?: 0,
        visibilityMeters = visibilityMeters ?: 0,
        sunriseTimestamp = systemMetadata?.sunriseTimestamp ?: 0,
        sunsetTimestamp = systemMetadata?.sunsetTimestamp ?: 0,
        timestamp = observationTimestamp ?: 0,
    )
}

fun CurrentWeatherEntity.toCurrentWeatherUi(
    context: Context,
    settings: UserSettings,
): CurrentWeatherUi {

    val timeZone = resolveTimeZone(timezoneOffsetSeconds)

    return CurrentWeatherUi(
        locationId = locationId,
        cityCountryText = "$cityName, $countryCode",
        dateText = formatDate(timestamp, timeZone),
        timeText = formatTime(timestamp, timeZone),
        conditionId = conditionId,
        iconCode = conditionIconCode,
        conditionTitle = context.getString(
            WeatherConditionResources.getResources(conditionId).titleResId
        ),
        conditionDescription = context.getString(
            WeatherConditionResources.getResources(conditionId).descriptionResId
        ),
        temperatureText = formatTemperature(temperatureCelsius, context, settings.temperatureUnit),
        feelsLikeText = formatTemperature(feelsLikeTemperature, context, settings.temperatureUnit),
        minTempText = formatTemperature(minimumTemperature, context, settings.temperatureUnit),
        maxTempText = formatTemperature(maximumTemperature, context, settings.temperatureUnit),
        humidityText = context.getString(R.string.unit_percentage, humidityPercent),
        pressureText = context.getString(R.string.unit_pressure, atmosphericPressure),
        visibilityText = context.getString(R.string.unit_kilometer, (visibilityMeters / 1000.0).toString()),
        cloudCoverText = context.getString(R.string.unit_percentage, cloudCoveragePercent),
        windSpeedText = formatWindSpeed(windSpeedMetersPerSecond, context, settings.windSpeedUnit),
        windDirectionText = formatWindDirection(windDirectionDegrees, context),
        windGustText = formatWindSpeed(
            windGustMetersPerSecond ?: 0.0,
            context,
            settings.windSpeedUnit
        ),
        rainVolumeText = if ((rainLastHourMillimeters ?: 0.0) > 0.0)
            context.getString(R.string.unit_millimeters, rainLastHourMillimeters)
        else
            context.getString(R.string.label_none),
        snowVolumeText = if ((snowLastHourMillimeters ?: 0.0) > 0.0)
            context.getString(R.string.unit_millimeters, snowLastHourMillimeters)
        else
            context.getString(R.string.label_none),
        sunriseText = formatTime(sunriseTimestamp, timeZone),
        sunsetText = formatTime(sunsetTimestamp, timeZone),
    )
}