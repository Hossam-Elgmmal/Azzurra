package com.iti.azzurra.data.weather.local.models.geo_location

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class GeoLocalNamesConverter {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromMap(map: Map<String, String>?): String {
        return map?.let { json.encodeToString(it) } ?: ""
    }

    @TypeConverter
    fun toMap(value: String?): Map<String, String> {
        return value?.let { json.decodeFromString(it) } ?: emptyMap()
    }
}