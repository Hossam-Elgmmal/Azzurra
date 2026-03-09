package com.iti.azzurra.data.weather.local.models.current_location

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey
    val locationId: String,// lat_lon each rounded to 2 decimal places * 100
    val cityName: String,
    val countryCode: String,
    val latitude: Double,
    val longitude: Double,
    val timezoneOffset: Int
)