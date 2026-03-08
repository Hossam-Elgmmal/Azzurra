package com.iti.azzurra.data.remote.models.hourly

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyMainDto(
    @SerialName("temp") val temperature: Double? = null,
    @SerialName("feels_like") val feelsLikeTemperature: Double? = null,
    @SerialName("temp_min") val minimumTemperature: Double? = null,
    @SerialName("temp_max") val maximumTemperature: Double? = null,
    @SerialName("pressure") val atmosphericPressure: Int? = null,
    @SerialName("sea_level") val seaLevelPressure: Int? = null,
    @SerialName("grnd_level") val groundLevelPressure: Int? = null,
    @SerialName("humidity") val humidityPercentage: Int? = null
)