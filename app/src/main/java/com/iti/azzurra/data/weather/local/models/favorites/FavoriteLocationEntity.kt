package com.iti.azzurra.data.weather.local.models.favorites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_locations")
data class FavoriteLocationEntity(
    @PrimaryKey
    val locationId: String,
    val cityName: String,
    val countryCode: String,
    val latitude: Double,
    val longitude: Double,
    val timezoneOffset: Int
)