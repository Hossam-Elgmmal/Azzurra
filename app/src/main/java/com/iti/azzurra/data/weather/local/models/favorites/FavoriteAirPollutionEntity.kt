package com.iti.azzurra.data.weather.local.models.favorites

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "favorite_air_pollution",
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
data class FavoriteAirPollutionEntity(
    val timestamp: Long,
    val locationId: String,
    val airQualityIndex: Int,
    val carbonMonoxide: Double,
    val nitrogenMonoxide: Double,
    val nitrogenDioxide: Double,
    val ozone: Double,
    val sulphurDioxide: Double,
    val particulateMatter2AndHalf: Double,
    val particulateMatter10: Double,
    val ammonia: Double
)