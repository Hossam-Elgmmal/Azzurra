package com.iti.azzurra.data.weather.local.models.favorites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_locations")
data class FavoriteLocationEntity(
    @PrimaryKey
    val locationId: String = "",
    val cityNameEn: String = "",
    val cityNameAr: String = "",
    val countryCode: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
)