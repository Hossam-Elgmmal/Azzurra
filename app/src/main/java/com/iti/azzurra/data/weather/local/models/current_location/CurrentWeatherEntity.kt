package com.iti.azzurra.data.weather.local.models.current_location

import androidx.room.Entity

@Entity(
    tableName = "current_weather",
    primaryKeys = ["locationId", "timestamp"],
)
data class CurrentWeatherEntity(
    val locationId: String,
    val timestamp: Long,
    val latitude: Double,
    val longitude: Double,
    val cityId: Long,
    val cityName: String,
    val countryCode: String,
    val timezoneOffsetSeconds: Int,
    val conditionId: Int,
    val conditionGroup: String,
    val conditionDescription: String,
    val conditionIconCode: String,
    val temperatureCelsius: Double,
    val feelsLikeTemperature: Double,
    val minimumTemperature: Double,
    val maximumTemperature: Double,
    val atmosphericPressure: Int,
    val humidityPercent: Int,
    val windSpeedMetersPerSecond: Double,
    val windDirectionDegrees: Int,
    val windGustMetersPerSecond: Double?,
    val rainLastHourMillimeters: Double?,
    val snowLastHourMillimeters: Double?,
    val cloudCoveragePercent: Int,
    val visibilityMeters: Int,
    val sunriseTimestamp: Long,
    val sunsetTimestamp: Long,
)