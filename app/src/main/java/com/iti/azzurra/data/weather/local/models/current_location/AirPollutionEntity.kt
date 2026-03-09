package com.iti.azzurra.data.weather.local.models.current_location

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "air_pollution",
    primaryKeys = ["timestamp", "locationId"],
    foreignKeys = [
        ForeignKey(
            entity = LocationEntity::class,
            parentColumns = ["locationId"],
            childColumns = ["locationId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("locationId")]
)
data class AirPollutionEntity(
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