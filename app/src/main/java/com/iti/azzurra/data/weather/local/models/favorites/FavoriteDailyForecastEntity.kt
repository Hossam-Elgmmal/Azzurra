package com.iti.azzurra.data.weather.local.models.favorites

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "favorite_daily_forecast",
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
data class FavoriteDailyForecastEntity(
    val timestamp: Long,
    val locationId: String,
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