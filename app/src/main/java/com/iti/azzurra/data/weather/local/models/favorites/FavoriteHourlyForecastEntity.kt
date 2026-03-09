package com.iti.azzurra.data.weather.local.models.favorites

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "favorite_hourly_forecast",
    primaryKeys = ["timestamp", "locationId"],
    foreignKeys = [
        ForeignKey(
            entity = FavoriteLocationEntity::class,
            parentColumns = ["locationId"],
            childColumns = ["locationId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
)
data class FavoriteHourlyForecastEntity(
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