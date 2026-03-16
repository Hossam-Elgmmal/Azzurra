package com.iti.azzurra.data.weather.mappers

import com.iti.azzurra.data.weather.local.models.favorites.DailyForecastEntity
import com.iti.azzurra.data.weather.remote.models.daily.DailyForecastResponseDto
import com.iti.azzurra.data.weather.remote.models.daily.DailyItemDto

fun DailyForecastResponseDto.toFavoriteDailyForecastEntities(
    locationId: String,
): List<DailyForecastEntity> {

    val city = this.city
    val cityName = city?.cityName ?: ""
    val countryCode = city?.countryCode ?: ""
    val latitude = city?.coordinates?.latitude ?: 0.0
    val longitude = city?.coordinates?.longitude ?: 0.0
    val timezoneOffset = city?.timezoneOffset ?: 0

    return this.dailyItems
        .orEmpty()
        .take(16)
        .map { item ->
            item.toEntity(
                locationId = locationId,
                cityName = cityName,
                countryCode = countryCode,
                latitude = latitude,
                longitude = longitude,
                timezoneOffset = timezoneOffset
            )
        }
}

private fun DailyItemDto.toEntity(
    locationId: String,
    cityName: String,
    countryCode: String,
    latitude: Double,
    longitude: Double,
    timezoneOffset: Int
): DailyForecastEntity {

    val condition = this.weatherConditions?.firstOrNull()

    return DailyForecastEntity(
        locationId = locationId,
        dayTimestamp = (this.timestamp ?: 0L).toStartOfDayTimestamp(),
        cityName = cityName,
        countryCode = countryCode,
        latitude = latitude,
        longitude = longitude,
        timezoneOffset = timezoneOffset,
        sunriseTimestamp = this.sunriseTimestamp ?: 0L,
        sunsetTimestamp = this.sunsetTimestamp ?: 0L,
        // Temperature
        morningTemperature = this.temperature?.morningTemperature ?: 0.0,
        dayTemperature = this.temperature?.dayTemperature ?: 0.0,
        eveningTemperature = this.temperature?.eveningTemperature ?: 0.0,
        nightTemperature = this.temperature?.nightTemperature ?: 0.0,
        minimumTemperature = this.temperature?.minimumTemperature ?: 0.0,
        maximumTemperature = this.temperature?.maximumTemperature ?: 0.0,
        // Feels like
        morningFeelsLike = this.feelsLike?.morningFeelsLike ?: 0.0,
        dayFeelsLike = this.feelsLike?.dayFeelsLike ?: 0.0,
        eveningFeelsLike = this.feelsLike?.eveningFeelsLike ?: 0.0,
        nightFeelsLike = this.feelsLike?.nightFeelsLike ?: 0.0,
        // Atmosphere
        atmosphericPressure = this.atmosphericPressure ?: 0,
        humidityPercentage = this.humidityPercentage ?: 0,
        // Wind
        windSpeed = this.windSpeed ?: 0.0,
        windDirectionDegrees = this.windDirectionDegrees ?: 0,
        windGustSpeed = this.windGustSpeed ?: 0.0,
        // Precipitation
        cloudCoveragePercentage = this.cloudCoveragePercentage ?: 0,
        precipitationProbability = this.precipitationProbability ?: 0.0,
        rainVolumeMm = this.rainVolumeMm ?: 0.0,
        snowVolumeMm = this.snowVolumeMm ?: 0.0,
        // Weather condition (The first weather condition in API response is primary.)
        conditionId = condition?.conditionId ?: 0,
        conditionGroup = condition?.conditionGroup ?: "",
        conditionDescription = condition?.conditionDescription ?: "",
        iconCode = condition?.iconCode ?: "03d"
    )
}