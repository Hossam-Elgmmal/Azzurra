package com.iti.azzurra.data.weather.remote.models.hourly

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyPrecipitationDto(
    @SerialName("1h") val lastOneHourMm: Double? = null   // volume in mm
)