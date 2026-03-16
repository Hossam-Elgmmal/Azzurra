package com.iti.azzurra.data.weather.local.models.current_location

import androidx.room.Entity

@Entity(
    tableName = "air_pollution",
    primaryKeys = ["locationId", "timestamp"]
)
data class AirPollutionEntity(
    val locationId: String,
    val timestamp: Long,
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