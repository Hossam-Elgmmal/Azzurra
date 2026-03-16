package com.iti.azzurra.data.weather.remote.models.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WindDto(
    @SerialName("speed") val windSpeed: Double? = null,
    @SerialName("deg") val windDirectionDegrees: Int? = null,
    @SerialName("gust") val windGustSpeed: Double? = null
)