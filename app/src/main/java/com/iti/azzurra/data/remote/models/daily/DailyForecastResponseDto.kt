package com.iti.azzurra.data.remote.models.daily

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyForecastResponseDto(
    @SerialName("city") val city: DailyCityDto? = null,
    @SerialName("cod") val statusCode: String? = null,
    @SerialName("message") val message: Double? = null,
    @SerialName("cnt") val totalDays: Int? = null,          // max 16
    @SerialName("list") val dailyItems: List<DailyItemDto>? = null
)
