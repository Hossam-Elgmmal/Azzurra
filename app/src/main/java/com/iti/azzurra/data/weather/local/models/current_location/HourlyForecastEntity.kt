package com.iti.azzurra.data.weather.local.models.current_location

import androidx.room.Entity

@Entity(
    tableName = "hourly_forecast",
    primaryKeys = ["locationId", "timestamp"],
)
data class HourlyForecastEntity(
    val locationId: String = "",
    val timestamp: Long = 0,
    val temperature: Double = 0.0,
    val feelsLikeTemperature: Double = 0.0,
    val minimumTemperature: Double = 0.0,
    val maximumTemperature: Double = 0.0,
    val atmosphericPressure: Int = 0,
    val seaLevelPressure: Int = 0,
    val groundLevelPressure: Int = 0,
    val humidityPercentage: Int = 0,
    val windSpeed: Double = 0.0,
    val windDirectionDegrees: Int = 0,
    val windGustSpeed: Double = 0.0,
    val cloudCoveragePercentage: Int = 0,
    val visibilityMeters: Int = 0,
    val precipitationChance: Double = 0.0,
    val rainLastOneHourMm: Double = 0.0,
    val snowLastOneHourMm: Double = 0.0,
    val conditionId: Int = 0,
    val conditionGroup: String = "",
    val conditionDescription: String = "",
    val iconCode: String = "",
    val timestampText: String = "",
    val timezoneOffset: Int = 0
)