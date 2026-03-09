package com.iti.azzurra.data.alert.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alerts")
data class WeatherAlertEntity(
    @PrimaryKey
    val alertId: String, // uuid string
    val startTime: Long,
    val endTime: Long,
    val locationId: String,// lat_lon each rounded to 2 decimal places * 100
    val cityName: String,
    val countryCode: String,
    val latitude: Double,
    val longitude: Double,
    val alarmType: AlertType,
    val isActive: Boolean
)