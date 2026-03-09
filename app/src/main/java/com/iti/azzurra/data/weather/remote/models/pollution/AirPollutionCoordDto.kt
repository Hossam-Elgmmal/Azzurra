package com.iti.azzurra.data.weather.remote.models.pollution

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AirPollutionCoordDto(
    @SerialName("lat") val latitude: Double? = null,
    @SerialName("lon") val longitude: Double? = null
)