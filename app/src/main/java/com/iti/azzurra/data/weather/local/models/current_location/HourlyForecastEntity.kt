package com.iti.azzurra.data.weather.local.models.current_location

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "hourly_forecast",
    primaryKeys = ["timestamp", "locationId"],
    foreignKeys = [
        ForeignKey(
            entity = LocationEntity::class,
            parentColumns = ["locationId"],
            childColumns = ["locationId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
)
data class HourlyForecastEntity(
    val timestamp: Long,
    val locationId: String,
    val temperature: Double,
    val feelsLikeTemperature: Double,
    val minimumTemperature: Double,
    val maximumTemperature: Double,
    val atmosphericPressure: Int,
    val seaLevelPressure: Int,
    val groundLevelPressure: Int,
    val humidityPercentage: Int,
    val windSpeed: Double,
    val windDirectionDegrees: Int,
    val windGustSpeed: Double,
    val cloudCoveragePercentage: Int,
    val visibilityMeters: Int,
    val precipitationChance: Double,
    val rainLastOneHourMm: Double,
    val snowLastOneHourMm: Double,
    val conditionId: Int,
    val conditionGroup: String,
    val conditionDescription: String,
    val iconCode: String,
    val timestampText: String
)