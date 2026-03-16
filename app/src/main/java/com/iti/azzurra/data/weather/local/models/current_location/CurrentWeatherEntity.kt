package com.iti.azzurra.data.weather.local.models.current_location

import androidx.room.Entity

@Entity(
    tableName = "current_weather",
    primaryKeys = ["locationId", "timestamp"],
)
data class CurrentWeatherEntity(
    val locationId: String = "",
    val timestamp: Long = 0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val cityId: Long = 0,
    val cityName: String = "",
    val countryCode: String = "",
    val timezoneOffsetSeconds: Int = 0,
    val conditionId: Int = 0,
    val conditionGroup: String = "",
    val conditionDescription: String = "",
    val conditionIconCode: String = "",
    val temperatureCelsius: Double = 0.0,
    val feelsLikeTemperature: Double = 0.0,
    val minimumTemperature: Double = 0.0,
    val maximumTemperature: Double = 0.0,
    val atmosphericPressure: Int = 0,
    val humidityPercent: Int = 0,
    val windSpeedMetersPerSecond: Double = 0.0,
    val windDirectionDegrees: Int = 0,
    val windGustMetersPerSecond: Double? = 0.0,
    val rainLastHourMillimeters: Double? = 0.0,
    val snowLastHourMillimeters: Double? = 0.0,
    val cloudCoveragePercent: Int = 0,
    val visibilityMeters: Int = 0,
    val sunriseTimestamp: Long = 0,
    val sunsetTimestamp: Long = 0
)