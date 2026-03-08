package com.iti.azzurra.data.remote.models.hourly

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyWindDto(
    @SerialName("speed") val windSpeed: Double? = null,
    @SerialName("deg") val windDirectionDegrees: Int? = null,
    @SerialName("gust") val windGustSpeed: Double? = null
)