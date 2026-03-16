package com.iti.azzurra.data.weather.local.models.favorites

import androidx.room.Entity

@Entity(tableName = "daily_forecast", primaryKeys = ["locationId", "dayTimestamp"])
data class DailyForecastEntity(
    val locationId: String = "",
    val dayTimestamp: Long = 0,
    val cityName: String = "",
    val countryCode: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val timezoneOffset: Int = 0,
    val sunriseTimestamp: Long = 0,
    val sunsetTimestamp: Long = 0,
    val morningTemperature: Double = 0.0,
    val dayTemperature: Double = 0.0,
    val eveningTemperature: Double = 0.0,
    val nightTemperature: Double = 0.0,
    val minimumTemperature: Double = 0.0,
    val maximumTemperature: Double = 0.0,
    val morningFeelsLike: Double = 0.0,
    val dayFeelsLike: Double = 0.0,
    val eveningFeelsLike: Double = 0.0,
    val nightFeelsLike: Double = 0.0,
    val atmosphericPressure: Int = 0,
    val humidityPercentage: Int = 0,
    val windSpeed: Double = 0.0,
    val windDirectionDegrees: Int = 0,
    val windGustSpeed: Double = 0.0,
    val cloudCoveragePercentage: Int = 0,
    val precipitationProbability: Double = 0.0,
    val rainVolumeMm: Double = 0.0,
    val snowVolumeMm: Double = 0.0,
    val conditionId: Int = 0,
    val conditionGroup: String = "",
    val conditionDescription: String = "",
    val iconCode: String = ""
)