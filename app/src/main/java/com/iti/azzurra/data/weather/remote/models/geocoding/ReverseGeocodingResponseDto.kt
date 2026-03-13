package com.iti.azzurra.data.weather.remote.models.geocoding

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReverseGeocodingResponseDto(
    @SerialName("name") val nameEn: String? = null,
    @SerialName("local_names") val localNames: Map<String, String>? = null,
    @SerialName("lat") val lat: Double? = null,
    @SerialName("lon") val lon: Double? = null,
    @SerialName("country") val country: String? = null
)