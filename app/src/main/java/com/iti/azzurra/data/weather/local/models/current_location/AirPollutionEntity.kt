package com.iti.azzurra.data.weather.local.models.current_location

import androidx.room.Entity

@Entity(
    tableName = "air_pollution",
    primaryKeys = ["locationId", "timestamp"]
)
data class AirPollutionEntity(
    val locationId: String = "",
    val timestamp: Long = 0,
    val airQualityIndex: Int = 0,
    val carbonMonoxide: Double = 0.0,
    val nitrogenMonoxide: Double = 0.0,
    val nitrogenDioxide: Double = 0.0,
    val ozone: Double = 0.0,
    val sulphurDioxide: Double = 0.0,
    val particulateMatter2AndHalf: Double = 0.0,
    val particulateMatter10: Double = 0.0,
    val ammonia: Double = 0.0
)