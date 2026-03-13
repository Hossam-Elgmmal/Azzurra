package com.iti.azzurra.data.weather.local.models.geo_location

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "geolocations")
data class GeoLocationEntity(
    @PrimaryKey
    val locationId: String = "",// lat_lon each rounded to 2 decimal places * 100    val nameEn: String,
    val nameEn: String = "",
    val localizedNames: Map<String, String> = emptyMap(),
    val country: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)