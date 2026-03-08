package com.iti.azzurra.data.remote.models.hourly

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyForecastResponseDto(
    @SerialName("cod") val statusCode: String? = null,
    @SerialName("message") val message: Int? = null,
    @SerialName("cnt") val totalItems: Int? = null,        // max 96
    @SerialName("list") val hourlyItems: List<HourlyItemDto>? = null,
    @SerialName("city") val city: HourlyCityDto? = null
)