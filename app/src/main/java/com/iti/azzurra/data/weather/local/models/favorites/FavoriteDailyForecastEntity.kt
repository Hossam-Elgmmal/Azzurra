package com.iti.azzurra.data.weather.local.models.favorites

import androidx.room.Entity

@Entity(tableName = "favorite_daily_forecast", primaryKeys = ["locationId", "dayTimestamp"])
data class FavoriteDailyForecastEntity(
    val locationId: String,
    val dayTimestamp: Long,
    val cityName: String,
    val countryCode: String,
    val latitude: Double,
    val longitude: Double,
    val timezoneOffset: Int,
    val sunriseTimestamp: Long,
    val sunsetTimestamp: Long,
    val morningTemperature: Double,
    val dayTemperature: Double,
    val eveningTemperature: Double,
    val nightTemperature: Double,
    val minimumTemperature: Double,
    val maximumTemperature: Double,
    val morningFeelsLike: Double,
    val dayFeelsLike: Double,
    val eveningFeelsLike: Double,
    val nightFeelsLike: Double,
    val atmosphericPressure: Int,
    val humidityPercentage: Int,
    val windSpeed: Double,
    val windDirectionDegrees: Int,
    val windGustSpeed: Double,
    val cloudCoveragePercentage: Int,
    val precipitationProbability: Double,
    val rainVolumeMm: Double,
    val snowVolumeMm: Double,
    val conditionId: Int,
    val conditionGroup: String,
    val conditionDescription: String,
    val iconCode: String
)