package com.iti.azzurra.data.weather.remote.models.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoordinatesDto(
    @SerialName("lat") val latitude: Double? = null,
    @SerialName("lon") val longitude: Double? = null
)