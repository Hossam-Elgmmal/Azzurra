package com.iti.azzurra.data.weather.remote.models.current

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CurrentWeatherAccumulationDto(
    @SerialName("1h") val lastHourMillimeters: Double? = null,
    @SerialName("3h") val lastThreeHoursMillimeters: Double? = null
)